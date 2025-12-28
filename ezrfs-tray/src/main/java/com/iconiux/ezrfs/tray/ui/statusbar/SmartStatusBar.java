/*
 * Created by JFormDesigner on Wed Dec 29 00:33:47 YEKT 2021
 */

package com.iconiux.ezrfs.tray.ui.statusbar;
import com.iconiux.ezrfs.tray.eventbus.NetInfoMessageBean;
	import com.iconiux.ezrfs.tray.eventbus.RFSInfoMessageBean;
	import com.iconiux.ezrfs.tray.eventbus.ServerInfoMessageBean;
	import com.iconiux.ezrfs.tray.eventbus.TrayInfoMessageBean;
	import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.bushe.swing.event.annotation.AnnotationProcessor;
import org.bushe.swing.event.annotation.EventSubscriber;
//import org.springframework.context.event.EventListener;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

import static com.iconiux.ezrfs.tray.ConstantHolder.OFF;
import static com.iconiux.ezrfs.tray.ConstantHolder.ON;

/**
 * @author 1
 */
@Slf4j
@Getter
public class SmartStatusBar extends JPanel {
	public SmartStatusBar() {
		initComponents();
		postInitComponents();
	}

	private void postInitComponents() {
		eventBusInit();

		trayStatePanel.setIndicatorName("Трей");
		trayStatePanel.toState(StateEnum.INFO, ON);

		netStatePanel.setIndicatorName("Интернет");
		netStatePanel.toState(StateEnum.OFF, OFF);

		serverStatePanel.setIndicatorName("Сервер");
		serverStatePanel.toState(StateEnum.OFF, OFF);

		rfsStatePanel.setIndicatorName("Хранилище");
		rfsStatePanel.toState(StateEnum.OFF, OFF);
	}

	/**
	 * подписываем компонент на сообщения от EventBus
	 */
	public void eventBusInit() {
		AnnotationProcessor.process(this); //this line can be avoided with a compile-time tool or an Aspect
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        statusBarInfoPanel = new StatusBarInfoPanel();
        stateBarPanel = new JPanel();
        trayStatePanel = new StatePanel();
        netStatePanel = new StatePanel();
        serverStatePanel = new StatePanel();
        rfsStatePanel = new StatePanel();

        //======== this ========
        setBorder(BorderFactory.createEmptyBorder());
        setName("this");
        setLayout(new BorderLayout());

        //---- statusBarInfoPanel ----
        statusBarInfoPanel.setName("statusBarInfoPanel");
        add(statusBarInfoPanel, BorderLayout.CENTER);

        //======== stateBarPanel ========
        {
            stateBarPanel.setBorder(new EmptyBorder(0, 10, 0, 0));
            stateBarPanel.setName("stateBarPanel");
            stateBarPanel.setLayout(new FormLayout(
                "default:grow, 3*(1dlu, default)",
                "fill:default:grow"));

            //---- trayStatePanel ----
            trayStatePanel.setIndicatorName("communication");
            trayStatePanel.setName("trayStatePanel");
            stateBarPanel.add(trayStatePanel, CC.xy(1, 1));

            //---- netStatePanel ----
            netStatePanel.setIndicatorName("communication");
            netStatePanel.setName("netStatePanel");
            stateBarPanel.add(netStatePanel, CC.xy(3, 1));

            //---- serverStatePanel ----
            serverStatePanel.setIndicatorName("communication");
            serverStatePanel.setName("serverStatePanel");
            stateBarPanel.add(serverStatePanel, CC.xy(5, 1));

            //---- rfsStatePanel ----
            rfsStatePanel.setIndicatorName("communication");
            rfsStatePanel.setName("rfsStatePanel");
            stateBarPanel.add(rfsStatePanel, CC.xy(7, 1));
        }
        add(stateBarPanel, BorderLayout.EAST);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private StatusBarInfoPanel statusBarInfoPanel;
    private JPanel stateBarPanel;
    private StatePanel trayStatePanel;
    private StatePanel netStatePanel;
    private StatePanel serverStatePanel;
    private StatePanel rfsStatePanel;
	// JFormDesigner - End of variables declaration  //GEN-END:variables

	@EventSubscriber(eventClass = TrayInfoMessageBean.class)
	public void onTrayInfoMessageBean(TrayInfoMessageBean messageBean) {
		log.info("onTrayInfoMessageBean {}", messageBean);
		trayStatePanel.toState(messageBean);
	}

	@EventSubscriber(eventClass = NetInfoMessageBean.class)
	public void onNetInfoMessageBean(NetInfoMessageBean messageBean) {
		log.info("onNetInfoMessageBean {}", messageBean);
		netStatePanel.toState(messageBean);
	}
	@EventSubscriber(eventClass = ServerInfoMessageBean.class)
	public void onServerInfoMessageBean(ServerInfoMessageBean messageBean) {
		log.info("onServerInfoMessageBean {}", messageBean);
		serverStatePanel.toState(messageBean);
	}

	@EventSubscriber(eventClass = RFSInfoMessageBean.class)
	public void onRFSInfoMessageBean(RFSInfoMessageBean messageBean) {
		log.info("onServerInfoMessageBean {}", messageBean);
		rfsStatePanel.toState(messageBean);
	}
}
