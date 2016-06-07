package cdio.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Menu extends Composite {
 
public Menu (final MainViewController mvc) {
	VerticalPanel vPanel = new VerticalPanel();
	initWidget(vPanel);
	vPanel.setStyleName("Menu");
	Anchor showPersons = new Anchor("Vis/Ret personer");
	
	vPanel.add(showPersons);
	Anchor showPersons1 = new Anchor("");
	Anchor showPersons2 = new Anchor("Vis personer2");
	Anchor showPersons3 = new Anchor("Vis personer2");
	Anchor showPersons4 = new Anchor("Vis personer2");
	showPersons.addClickHandler(new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent event) {
		mvc.showPersons();
			
		}
	});

	vPanel.add(showPersons1);
	vPanel.add(showPersons2);
	vPanel.add(showPersons3);
	vPanel.add(showPersons4);
	
}

}
