package pojos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ItemInfo {
	int id;
	int amountNeeded;
	int typeIndex;
	String name;

}
