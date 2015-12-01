package com.jinlufund.service.uc;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jinlufund.entity.uc.Rank;
import com.jinlufund.exception.JinluException;
import com.jinlufund.repository.uc.RankRepository;

@Service
public class RankService {

	@Autowired
	private RankRepository rankRepository;
	
	private Rank root;
	
	private Map<Long, Rank> rankMap;
	
	@PostConstruct
	public void refresh() {
		rankMap = new ConcurrentHashMap<Long, Rank>();
		Iterable<Rank> ranks = rankRepository.findAll();
		for (Rank r : ranks) {
			if (r.getParentId() == null) {
				root = r;
			}
			rankMap.put(r.getId(), r);
		}
		
		for (Rank r : ranks) {
			Long parentId = r.getParentId();
			if (parentId != null) {
				Rank parent = rankMap.get(parentId);
				associate(parent, r);
			}
		}
	}
	
	private void associate(Rank parent, Rank child) {
		List<Rank> children = parent.getChildren();
		children.add(child);
		Collections.sort(children, new Comparator<Rank>() {
			@Override
			public int compare(Rank o1, Rank o2) {
				return o1.getOrderNo() - o2.getOrderNo();
			}
		});
		child.setParent(parent);
	}
	
	public String getJsonTree(boolean open) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode rootNode = mapper.createObjectNode();
		recursionNode(root, rootNode, mapper, open);
		String result = null;
		try {
			result = mapper.writeValueAsString(rootNode);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println(result);
		return result;
	}
	
	/**
	 * 给出当前rank和一个空的JSON节点，生成完整的JSON节点，并递归。
	 * @param rank
	 * @param parentNode
	 * @param mapper
	 */
	private void recursionNode(Rank rank, ObjectNode parentNode, ObjectMapper mapper, boolean open) {
		parentNode.put("id", rank.getId());
		parentNode.put("name", rank.getName());
		if (open) {
			parentNode.put("open", true);
		}
		List<Rank> children = rank.getChildren();
		if (children.size() == 0) {
			return;
		} else {
			ArrayNode childrenNode = mapper.createArrayNode();
			parentNode.set("children", childrenNode);
			for (Rank child : children) {
				ObjectNode node = mapper.createObjectNode();
				childrenNode.add(node);
				recursionNode(child, node, mapper, open);
			}
		}
	}
	
	public Rank find(Long id) {
		return findInternal(id);
	}
	
	public Rank addChild(Long parentId, Rank input) {
		Rank rank = new Rank();
		rank.setName(input.getName());
		rank.setDescription(input.getDescription());
		rank.setParentId(parentId);
		Rank parent = assertRankExist(parentId);
		List<Rank> children = parent.getChildren();
		int orderNo = 0;
		if (children.size() > 0) {
			Rank lastChild = children.get(children.size() - 1);
			orderNo = lastChild.getOrderNo() + 1;
		}
		rank.setOrderNo(orderNo);
		rank = rankRepository.save(rank);
		rankMap.put(rank.getId(), rank);
		associate(parent, rank);
		return rank;
	}
	
	public Rank update(Long id, Rank input) {
		Rank rank = assertRankExist(id);
		rank.setName(input.getName());
		rank.setDescription(input.getDescription());
		rankRepository.save(rank);
		return rank;
	}
	
	public void delete(Long id) {
		Rank rank = assertRankExist(id);
		List<Rank> children = rank.getChildren();
		if (children.size() > 0) {
			JinluException e = new JinluException();
			e.setErrorCode("rankService.delete.rankHasChild");
			e.addParameter("id", id);
			throw e;
		}
		Rank parent = rank.getParent();
		parent.getChildren().remove(rank);
		deleteInternal(id);
		rankRepository.delete(rank);
	}
	
	private Rank assertRankExist(Long id) {
		Rank rank = findInternal(id);
		if (rank == null) {
			JinluException e = new JinluException();
			e.setErrorCode("rankSerivce.rank.notExist");
			e.addParameter("id", id);
			throw e;
		}
		return rank;
	}
	
	private Rank findInternal(Long id) {
		return rankMap.get(id);
	}
	
	private void deleteInternal(Long id) {
		rankMap.remove(id);
	}
}
