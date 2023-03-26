package pojos;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class ResearchTree {
	String resourceType;
	String resourceVersion;
	//List<ResearchEntry> research;

	Map<Integer, Integer> research;

	@Tolerate
	@JsonSetter("research")
	public void setResearch(List<ResearchEntry> research) {
		this.research = research.stream()
				.collect(Collectors.toMap(ResearchEntry::getId, ResearchEntry::getCount, (x, y) -> y,  LinkedHashMap::new));
	}


	@JsonGetter("research")
	public List<ResearchEntry > getResearchList() {
		return research.entrySet().stream()
				.map(entry -> new ResearchEntry(entry.getKey(), entry.getValue()))
				.collect(Collectors.toList());
	}
}
