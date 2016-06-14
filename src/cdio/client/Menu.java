package cdio.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.thirdparty.javascript.rhino.Token;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Menu extends Composite {

	private Anchor showRaavBa;
	private Anchor showProBa;
	private Anchor showRecepter;
	private Anchor showRaavarer;
	private Anchor opretBruger;
	private Anchor redigerBruger;
	private Anchor logUd;
	private Anchor visBruger;
	

	public Menu (final MainViewController mvc, String role) {
		VerticalPanel vPanel = new VerticalPanel();
		initWidget(vPanel);

		vPanel.setStyleName("Menu");
		showRaavBa = new Anchor("Raavarebatches");
		showProBa = new Anchor("Produktbatches");
		showRecepter = new Anchor("Recepter");
		showRaavarer = new Anchor("Raavarer");
		opretBruger = new Anchor("Opret Bruger");
		redigerBruger = new Anchor("Bruger liste");
		visBruger = new Anchor("Bruger liste");
		
		logUd = new Anchor("Log Ud");
		determineUserMenu(role);
	
		
		showRaavBa.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				mvc.showRaavBa();

			}
			
		});
		
		showProBa.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				mvc.showProBa();

			}
			
		});
		
		showRecepter.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				mvc.showRecepter();

			}
			
		});
		
		
		showRaavarer.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				mvc.showRaavarer();

			}
			
		});
		
		
		opretBruger.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				mvc.opretBruger();

			}
			
		});
		
		redigerBruger.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				mvc.visBruger();

			}
			
		});
		visBruger.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				mvc.viewBruger();

			}
			
		});
		logUd.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				mvc.logUd();

			}
			
		});
		vPanel.add(showRaavBa);
		vPanel.add(showProBa);
		vPanel.add(showRecepter);
		vPanel.add(showRaavarer);
		vPanel.add(opretBruger);
		vPanel.add(redigerBruger);
		vPanel.add(visBruger);
		vPanel.add(logUd);


	}

	private void determineUserMenu(String token){
		
		switch (token){
		
		case "farmaceut":
			opretBruger.setVisible(false);
			redigerBruger.setVisible(false);
			break;

		case "vaerkfoerer":
			opretBruger.setVisible(false);
			redigerBruger.setVisible(false);
			showRaavarer.setVisible(false);
			showRecepter.setVisible(false);
			break;

		case "operatoer":
			opretBruger.setVisible(false);
			redigerBruger.setVisible(false);
			showRaavarer.setVisible(false);
			showRecepter.setVisible(false);
			showRaavBa.setVisible(false);
			showProBa.setVisible(false);
			break;

		default:
			break;
		}


	}

}
