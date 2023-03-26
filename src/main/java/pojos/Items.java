package pojos;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Data
public class Items {
	List<String> types = new ArrayList<>();
	Map<Integer, ItemInfo> items = new HashMap<>();

	public void addType(String type) {
		this.types.add(type);
	}

	public void addItem(ItemInfo value) {
		this.items.put(value.getId(), value);
	}
}
