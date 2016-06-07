package cdio.client;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Menu extends Composite {
VerticalPanel vPanel;

public Menu () {
	vPanel = new VerticalPanel();
	initWidget(vPanel);
	vPanel.setStyleName("navn");
	Anchor showPersons = new Anchor("Vis personer");
	vPanel.add(showPersons);
	Anchor showPersons1 = new Anchor("Vis personer1");
	Anchor showPersons2 = new Anchor("Vis personer2");
	vPanel.add(showPersons1);
	vPanel.add(showPersons2);
	
}

}
