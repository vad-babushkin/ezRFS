/*
 * Created by JFormDesigner on Sat Sep 23 19:16:37 YEKT 2023
 */

package com.iconiux.ezrfs.tray;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.gui.TableFormat;
import ca.odell.glazedlists.swing.EventTableModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.iconiux.ezlfs.model.FileMetadata;
import com.iconiux.ezrfs.tray.eventbus.*;
import com.iconiux.ezrfs.tray.service.ClipboardManagerService;
import com.iconiux.ezrfs.tray.service.ConnectionService;
import com.iconiux.ezrfs.tray.ui.renderer.NaturalDateRenderer;
import com.iconiux.ezrfs.tray.ui.renderer.StorageItemUrlRenderer;
import com.iconiux.ezrfs.tray.ui.statusbar.SmartStatusBar;
import com.iconiux.ezrfs.tray.ui.table.StorageItemTableFormat;
import com.iconiux.ezrfs.tray.ui.vo.StorageItem;
import com.iconiux.ezrfs.tray.util.RFSHelper;
import com.jgoodies.uif.component.ToolBarButton;
import com.jgoodies.uif.component.ToolBarToggleButton;
import com.jgoodies.uif_lite.panel.SimpleInternalFrame;
import io.github.thibaultmeyer.cuid.CUID;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.bushe.swing.event.EventBus;
import org.bushe.swing.event.annotation.AnnotationProcessor;
import org.bushe.swing.event.annotation.EventSubscriber;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import static com.iconiux.ezrfs.tray.ConstantHolder.JSON_PATH_KEY;
import static com.iconiux.ezrfs.tray.ui.UIHelper.buildCompositeImageIcon;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author 1
 */
@Slf4j
@Getter
@Setter
public class AppMainForm extends JXFrame {

	JFileChooser fileChooser = null;
	ObjectMapper objectMapper = new ObjectMapper();

	private Configuration applicationConfiguration;
	private Boolean trayEnabled = true;
	private ConnectionService connectionService = null;
//	private FlavorListener flavorListener = null;
//	private ClipboardListener clipboardListener = null;

//	private SelectablePropTableModel2 searchPropTableModel;

//	private String previousSearch = "";

	//	ExecutorService executorService = Executors.newCachedThreadPool();
	private ClipboardManagerService clipboardManagerService;
	private Preferences preferences = Preferences.userNodeForPackage(AppMainForm.class);
	private EventList<StorageItem> storageItemEventList = new BasicEventList<>();

	public AppMainForm() {
		super();
		initComponents();

		try {
			initCustomComponents();
		} catch (Exception e) {
			log.error("", e);
		}

		pack();
		setLocationRelativeTo(null);
	}

	/**
	 * @param connectionService .
	 */
	public AppMainForm(ConnectionService connectionService) {
		super();

		this.connectionService = connectionService;
		initComponents();

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				setVisible(false);
//				binderService.shutdown(0);
//				System.exit(0);
			}
		});

		try {
			initCustomComponents();
		} catch (Exception e) {
			log.error("", e);
		}

		pack();
		setLocationRelativeTo(null);
	}

	/**
	 * Процедура инициализации
	 */
	private void initCustomComponents() {
		// **************************************************************************
		// * процедуры инициализации
		// **************************************************************************
		genericInit();                                          // обобщённая инициализация
		jacksonInit();
//		initSearchPanel();
//		keyBindingInit();                                       // привязка клавиш к действиям
		tablesInit();                                           // инициализация таблиц
//		listInit();                                           // инициализация таблиц
//		buttonsInit();
//		graphInit();                                            // инициализация графа
		initClipboardListener();
		initEventBus();
	}

	/**
	 *
	 */
	private void jacksonInit() {
		objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		objectMapper.findAndRegisterModules();
	}

	private void genericInit() {
		fileChooser = new JFileChooser();

		// 3d
//		MezgirUIHelper.tune3D(graphToolBar);
//		MezgirUIHelper.tune3D(linksToolBar);
//		UIHelper.tune3D(simpleInternalFrame1);
//
//		UIHelper.tune3D(smartStatusBar);
//		UIHelper.tune3D(operationToolBar);
//		MezgirUIHelper.tune3D(bannerPanel1);
//		MezgirUIHelper.tune3D(graphFrame);
//		MezgirUIHelper.tune3D(linksFrame);
//		MezgirUIHelper.tune3D(searchLabeledTextField);

//		infiniteProgressPanel = new InfiniteProgressPanel();
//		this.setGlassPane(infiniteProgressPanel);
//		this.setWaitPane(infiniteProgressPanel);

//		linkByEmail.setIcon(fetch("/img/24x24/x-collection-shadow/link.png", "/img/16x16/x-collection-shadow/mail2.png"));
//		linkByPassword.setIcon(fetch("/img/24x24/x-collection-shadow/link.png", "/img/16x16/x-collection-shadow/lock.png"));
//		linkByFio.setIcon(fetch("/img/24x24/x-collection-shadow/link.png", "/img/16x16/x-collection-shadow/users_family.png"));
//
//		showIdNode.setIcon(fetch("/img/24x24/x-collection-shadow/view.png", "/img/16x16/x-collection-shadow/paragraph.png"));
//		showAliasNode.setIcon(fetch("/img/24x24/x-collection-shadow/view.png", "/img/16x16/x-collection-shadow/masks.png"));
//		showProfileNode.setIcon(fetch("/img/24x24/x-collection-shadow/view.png", "/img/16x16/x-collection-shadow/id_card.png"));
//		showPasswordNode.setIcon(fetch("/img/24x24/x-collection-shadow/view.png", "/img/16x16/x-collection-shadow/lock.png"));
//		showEmailNode.setIcon(fetch("/img/24x24/x-collection-shadow/view.png", "/img/16x16/x-collection-shadow/mail2.png"));
//		showFioNode.setIcon(fetch("/img/24x24/x-collection-shadow/view.png", "/img/16x16/x-collection-shadow/users_family.png"));

		setIconImage(buildCompositeImageIcon(
			"/img/48x48/x-collection-shadow/cloud.png",
			"/img/32x32/x-collection-shadow/data.png"
		));
	}


	/**
	 * инициализация таблиц
	 */
	private void tablesInit() {
		// назначаем модели
		// модели таблиц

//		searchPropTableModel = new SelectablePropTableModel2(true);
		storageItemsTable.setHighlighters(HighlighterFactory.createAlternateStriping());
//		storageItemsTable.setDefaultRenderer(java.util.Date.class, new DateRenderer());
		storageItemsTable.setRowHeight(24);

		TableFormat<StorageItem> storageItemTableFormat = new StorageItemTableFormat();
		EventTableModel tableModel = new EventTableModel(storageItemEventList, storageItemTableFormat);
		storageItemsTable.setModel(tableModel);

		TableColumn column = storageItemsTable.getColumnModel().getColumn(0);
		column.setCellRenderer(new NaturalDateRenderer());
		column = storageItemsTable.getColumnModel().getColumn(3);
		column.setCellRenderer(new StorageItemUrlRenderer());
//		MzBeanTableModel.setVisibleColumn(searchPropTableModel, searchResultTable, true);


		ListSelectionModel storageItemsTableSelectionModel = storageItemsTable.getSelectionModel();
		storageItemsTableSelectionModel.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				log.info("{}", e);
				if (e.getValueIsAdjusting()) {
					return;
				}

				final DefaultListSelectionModel target = (DefaultListSelectionModel) e.getSource();
				int iSelectedIndex = target.getAnchorSelectionIndex();
				log.info("selected {}", iSelectedIndex);
				if (iSelectedIndex >= 0) {
					copyUrlToClipboardButton.setEnabled(true);
					downloadButton.setEnabled(true);
					gotoWebPageButton.setEnabled(true);
				}
			}
		});
	}

	/**
	 *
	 */
	private void initClipboardListener() {
		clipboardManagerService = new ClipboardManagerService();

		if (preferences.getBoolean(clipboardSpyButton.getName(), false) != clipboardSpyButton.isSelected()) {
			clipboardSpyButton.doClick();
		}
	}

	/**
	 *
	 */
	private void initEventBus() {
		AnnotationProcessor.process(this);
	}

	/**
	 * @param e .
	 */
	private void uploadAction(ActionEvent e) {
		File retval = null;
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			retval = fileChooser.getSelectedFile();
			if (!retval.isFile()) {
				if (retval.exists()) {
					String msg = "Path is not that of an ordinary file: " + retval.getPath();
					JOptionPane dialog = new JOptionPane();
					dialog.showMessageDialog(this, msg, "Invalid Path", JOptionPane.ERROR_MESSAGE);
				} else {
					String msg = "File not found: " + retval.getPath();
					JOptionPane dialog = new JOptionPane();
					dialog.showMessageDialog(this, msg, "File Not Found", JOptionPane.ERROR_MESSAGE);
				}
			}
			fileChooser.setCurrentDirectory(retval.getParentFile());
			log.info("selected {}", retval);
			EventBus.publish(InfoMessageBean.createProcess("выгружается " + retval));

			try {
				(new UploadFileTask(retval)).execute();
			} catch (Exception ex) {
				log.error(null, ex);
				EventBus.publish(RFSInfoMessageBean.createError(ex.getMessage()));
			}
		}
	}

	/**
	 * @param e .
	 */
	private void addUrlAction(ActionEvent e) {
		String uploadedUrl = JOptionPane.showInputDialog(this, "Введите адрес файла в облаке:", "Ввод данных", JOptionPane.QUESTION_MESSAGE);
		log.info("{}", uploadedUrl);

		addStorageItemByUrl(uploadedUrl);
	}

	/**
	 * @param uploadedUrl .
	 */
	private void addStorageItemByUrl(String uploadedUrl) {
		if (isNotBlank(uploadedUrl) && StringUtils.startsWith(uploadedUrl, "http")) {
			try {
				StorageItem storageItem = new StorageItem();
				storageItem.setUploadedDateTime(new Date());
				storageItem.setFileName(CUID.randomCUID2().toString());
				String humanizeSize = humanize.Humanize.metricPrefix(0);
				storageItem.setSizeHumanize(humanizeSize);

				String fileCuid = RFSHelper.cutCuidFromUrl(uploadedUrl);
				if (isNotBlank(fileCuid)) {
					uploadedUrl = connectionService.buildDownloadFileUrl(fileCuid);
					storageItem.setUrl(uploadedUrl);

					FileMetadata fileMetadata = connectionService.downloadMeta(storageItem);
					if (fileMetadata != null) {
						storageItem.setFileName(fileMetadata.getFileName());
						humanizeSize = humanize.Humanize.metricPrefix(fileMetadata.getFileContentLength());
						storageItem.setSizeHumanize(humanizeSize);

						if (findStorageItemByUrl(storageItem.getUrl())) {
							EventBus.publish(RFSInfoMessageBean.createWarn("Адрес облачного файла " + uploadedUrl + " уже существует"));
						} else {
							storageItemEventList.getReadWriteLock().writeLock().lock();
							try {
								storageItemEventList.add(storageItem);
								saveStorageItemsToDisk();
							} finally {
								storageItemEventList.getReadWriteLock().writeLock().unlock();
							}

							storageItemsTable.packAll();
							EventBus.publish(RFSInfoMessageBean.createInfo("Добавлен адрес облачного файла " + uploadedUrl));
						}
					} else {
						EventBus.publish(RFSInfoMessageBean.createError("Ошибка обработки адреса " + uploadedUrl));
					}
				} else {
					EventBus.publish(RFSInfoMessageBean.createError("CUID не найден в строке адреса " + uploadedUrl));
				}
			} catch (InterruptedException | IOException ex) {
				EventBus.publish(RFSInfoMessageBean.createError(ex.getMessage()));
			}
		}
	}

	/**
	 * @param downloadUrl .
	 * @return .
	 */
	private boolean findStorageItemByUrl(String downloadUrl) {
		for (StorageItem storageItem : storageItemEventList) {
			if (StringUtils.equals(storageItem.getUrl(), downloadUrl)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param e .
	 */
	private void checkAvailableAction(ActionEvent e) {
		HashSet<String> urlSet = Sets.newHashSet();
		List<StorageItem> forRemoveList = Lists.newArrayList();
		storageItemEventList.getReadWriteLock().writeLock().lock();
		try {
			for (StorageItem storageItem : storageItemEventList) {
				EventBus.publish(InfoMessageBean.createProcess("проверяем " + storageItem.getUrl()));
				if (!connectionService.isDownloadFileUrlSafe(storageItem.getUrl())) {
					forRemoveList.add(storageItem);
					EventBus.publish(InfoMessageBean.createWarn("помечен на удаление " + storageItem.getUrl()));
				} else if (urlSet.contains(storageItem.getUrl())) {
					forRemoveList.add(storageItem);
					EventBus.publish(InfoMessageBean.createWarn("помечен на удаление " + storageItem.getUrl()));
				} else if (!connectionService.checkExist(RFSHelper.file2MetaUrl(storageItem.getUrl()))) {
					forRemoveList.add(storageItem);
					EventBus.publish(InfoMessageBean.createWarn("помечен на удаление " + storageItem.getUrl()));
				} else {
					urlSet.add(storageItem.getUrl());
					EventBus.publish(InfoMessageBean.createInfo(storageItem.getUrl() + " существует"));
				}
			}
			for (StorageItem storageItem : forRemoveList) {
				storageItemEventList.remove(storageItem);
				EventBus.publish(InfoMessageBean.createProcess("удаляем " + storageItem.getUrl()));
			}
			saveStorageItemsToDisk();
		} finally {
			storageItemEventList.getReadWriteLock().writeLock().unlock();
		}
		EventBus.publish(InfoMessageBean.createInfoDone());
	}

	/**
	 * @param e .
	 */
	private void downloadAction(ActionEvent e) {
		try {
			ListSelectionModel storageItemsTableSelectionModel = storageItemsTable.getSelectionModel();
			int iSelectedIndex = storageItemsTableSelectionModel.getAnchorSelectionIndex();
			log.info("selected {}", iSelectedIndex);
			if (iSelectedIndex >= 0) {
				StorageItem storageItem = storageItemEventList.get(iSelectedIndex);
				EventBus.publish(InfoMessageBean.createProcess("загружается " + storageItem.getUrl()));
				(new DownloadFileTask(storageItem)).execute();
			}
		} catch (Exception ex) {
			log.error(null, ex);
			EventBus.publish(RFSInfoMessageBean.createError(ex.getMessage()));
		}
	}

	/**
	 * @param e .
	 */
	private void copyUrlToClipboardAction(ActionEvent e) {
		ListSelectionModel storageItemsTableSelectionModel = storageItemsTable.getSelectionModel();
		int iSelectedIndex = storageItemsTableSelectionModel.getAnchorSelectionIndex();
		log.info("selected {}", iSelectedIndex);
		if (iSelectedIndex >= 0) {
			StorageItem storageItem = storageItemEventList.get(iSelectedIndex);

			Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
			StringSelection data = new StringSelection(RFSHelper.file2ViewUrl(storageItem.getUrl()));
			cb.setContents(data, null);
			EventBus.publish(InfoMessageBean.createInfo("ссылка на страницу файла скопирована в буфер обмена"));
		}
	}

	/**
	 * @param e .
	 */
	private void gotoWebPageAction(ActionEvent e) {
		Desktop desktop = Desktop.getDesktop();
		if (desktop.isSupported(Desktop.Action.BROWSE)) {
			try {
				ListSelectionModel storageItemsTableSelectionModel = storageItemsTable.getSelectionModel();
				int iSelectedIndex = storageItemsTableSelectionModel.getAnchorSelectionIndex();
				log.info("selected {}", iSelectedIndex);
				if (iSelectedIndex >= 0) {
					StorageItem storageItem = storageItemEventList.get(iSelectedIndex);
					URI uri = new URI(RFSHelper.file2ViewUrl(storageItem.getUrl()));
					EventBus.publish(InfoMessageBean.createProcess("открываем " + uri.toString()));
					desktop.browse(uri);
					EventBus.publish(InfoMessageBean.createInfoDone());
				}
			} catch (Exception ex) {
				log.error(null, ex);
				EventBus.publish(RFSInfoMessageBean.createError(ex.getMessage()));
			}
		}
	}

	/**
	 * @param e .
	 */
	private void clipboardSpyButtonItemStateChanged(ItemEvent e) {
		preferences.putBoolean(clipboardSpyButton.getName(), clipboardSpyButton.isSelected());
		if (clipboardSpyButton.isSelected()) {
			EventBus.publish(new ClipboardManagerCommandBean().setEnable(true));
		} else {
			EventBus.publish(new ClipboardManagerCommandBean().setEnable(false));
		}
	}

	/**
	 *
	 */
	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        simpleInternalFrame1 = new SimpleInternalFrame();
        searchResultScrollPane = new JScrollPane();
        storageItemsTable = new JXTable();
        smartStatusBar = new SmartStatusBar();
        operationToolBar = new JToolBar();
        addUrlButton = new ToolBarButton();
        clipboardSpyButton = new ToolBarToggleButton();
        copyUrlToClipboardButton = new JXButton();
        checkAvailableButton = new JXButton();
        uploadButton = new JXButton();
        downloadButton = new JButton();
        gotoWebPageButton = new JButton();

        //======== this ========
        setTitle("\u041e\u0431\u043b\u0430\u0447\u043d\u043e\u0435 \u0445\u0440\u0430\u043d\u0438\u043b\u0438\u0449\u0435");
        setIconImage(new ImageIcon(getClass().getResource("/img/48x48/x-collection-shadow/cloud.png")).getImage());
        setStartPosition(JXFrame.StartPosition.CenterInScreen);
        setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
        setName("this");
        var contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== simpleInternalFrame1 ========
        {
            simpleInternalFrame1.setFrameIcon(new ImageIcon(getClass().getResource("/img/32x32/x-collection-shadow/history.png")));
            simpleInternalFrame1.setToolBar(operationToolBar);
            simpleInternalFrame1.setName("simpleInternalFrame1");
            var simpleInternalFrame1ContentPane = simpleInternalFrame1.getContentPane();
            simpleInternalFrame1ContentPane.setLayout(new BorderLayout());

            //======== searchResultScrollPane ========
            {
                searchResultScrollPane.setName("searchResultScrollPane");

                //---- storageItemsTable ----
                storageItemsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                storageItemsTable.setColumnControlVisible(true);
                storageItemsTable.setEditable(false);
                storageItemsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                storageItemsTable.setName("storageItemsTable");
                searchResultScrollPane.setViewportView(storageItemsTable);
            }
            simpleInternalFrame1ContentPane.add(searchResultScrollPane, BorderLayout.CENTER);

            //---- smartStatusBar ----
            smartStatusBar.setName("smartStatusBar");
            simpleInternalFrame1ContentPane.add(smartStatusBar, BorderLayout.SOUTH);
        }
        contentPane.add(simpleInternalFrame1, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);

        //======== operationToolBar ========
        {
            operationToolBar.setName("operationToolBar");

            //---- addUrlButton ----
            addUrlButton.setIcon(new ImageIcon(getClass().getResource("/img/32x32/x-collection-shadow/link_add.png")));
            addUrlButton.setToolTipText("\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u0430\u0434\u0440\u0435\u0441 \u0444\u0430\u0439\u043b\u0430");
            addUrlButton.setName("addUrlButton");
            addUrlButton.addActionListener(e -> addUrlAction(e));
            operationToolBar.add(addUrlButton);

            //---- clipboardSpyButton ----
            clipboardSpyButton.setIcon(new ImageIcon(getClass().getResource("/img/32x32/x-collection-shadow/spy.png")));
            clipboardSpyButton.setToolTipText("\u0412\u043a\u043b\u044e\u0447\u0438\u0442\u044c \u0441\u043b\u0435\u0436\u0435\u043d\u0438\u0435 \u0437\u0430 \u0431\u0443\u0444\u0435\u0440\u043e\u043c \u043e\u0431\u043c\u0435\u043d\u0430");
            clipboardSpyButton.setName("clipboardSpyButton");
            clipboardSpyButton.addItemListener(e -> clipboardSpyButtonItemStateChanged(e));
            operationToolBar.add(clipboardSpyButton);
            operationToolBar.addSeparator();

            //---- copyUrlToClipboardButton ----
            copyUrlToClipboardButton.setIcon(new ImageIcon(getClass().getResource("/img/32x32/x-collection-shadow/copy.png")));
            copyUrlToClipboardButton.setEnabled(false);
            copyUrlToClipboardButton.setToolTipText("\u041a\u043e\u043f\u0438\u0440\u043e\u0432\u0430\u0442\u044c \u0430\u0434\u0440\u0435\u0441 \u0441\u0442\u0440\u0430\u043d\u0438\u0446\u044b \u0444\u0430\u0439\u043b\u0430 \u0432 \u0431\u0443\u0444\u0435\u0440 \u043e\u0431\u043c\u0435\u043d\u0430");
            copyUrlToClipboardButton.setName("copyUrlToClipboardButton");
            copyUrlToClipboardButton.addActionListener(e -> copyUrlToClipboardAction(e));
            operationToolBar.add(copyUrlToClipboardButton);

            //---- checkAvailableButton ----
            checkAvailableButton.setIcon(new ImageIcon(getClass().getResource("/img/32x32/x-collection-shadow/checks.png")));
            checkAvailableButton.setToolTipText("\u041f\u0440\u043e\u0432\u0435\u0440\u0438\u0442\u044c \u0441\u0443\u0449\u0435\u0441\u0442\u0432\u043e\u0432\u0430\u043d\u0438\u0435 \u0444\u0430\u0439\u043b\u043e\u0432 \u0432 \u043e\u0431\u043b\u0430\u043a\u0435");
            checkAvailableButton.setName("checkAvailableButton");
            checkAvailableButton.addActionListener(e -> checkAvailableAction(e));
            operationToolBar.add(checkAvailableButton);
            operationToolBar.addSeparator();

            //---- uploadButton ----
            uploadButton.setIcon(new ImageIcon(getClass().getResource("/img/32x32/x-collection-shadow/upload.png")));
            uploadButton.setToolTipText("\u0412\u044b\u0433\u0440\u0443\u0437\u0438\u0442\u044c \u0444\u0430\u0439\u043b \u0432 \u043e\u0431\u043b\u0430\u0447\u043d\u043e\u0435 \u0445\u0440\u0430\u043d\u0438\u043b\u0438\u0449\u0435");
            uploadButton.setName("uploadButton");
            uploadButton.addActionListener(e -> uploadAction(e));
            operationToolBar.add(uploadButton);

            //---- downloadButton ----
            downloadButton.setIcon(new ImageIcon(getClass().getResource("/img/32x32/business/Download.png")));
            downloadButton.setEnabled(false);
            downloadButton.setToolTipText("\u0417\u0430\u0433\u0440\u0443\u0437\u0438\u0442\u044c \u0444\u0430\u0439\u043b \u043d\u0430 \u043a\u043e\u043c\u043f\u044c\u044e\u0442\u0435\u0440");
            downloadButton.setName("downloadButton");
            downloadButton.addActionListener(e -> downloadAction(e));
            operationToolBar.add(downloadButton);

            //---- gotoWebPageButton ----
            gotoWebPageButton.setIcon(new ImageIcon(getClass().getResource("/img/32x32/x-collection-shadow/nav_right_green.png")));
            gotoWebPageButton.setEnabled(false);
            gotoWebPageButton.setToolTipText("\u041f\u0435\u0440\u0435\u0439\u0442\u0438 \u043d\u0430 \u0441\u0442\u0440\u0430\u043d\u0438\u0446\u0443 \u0444\u0430\u0439\u043b\u0430");
            gotoWebPageButton.setName("gotoWebPageButton");
            gotoWebPageButton.addActionListener(e -> gotoWebPageAction(e));
            operationToolBar.add(gotoWebPageButton);
        }
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private SimpleInternalFrame simpleInternalFrame1;
    private JScrollPane searchResultScrollPane;
    private JXTable storageItemsTable;
    private SmartStatusBar smartStatusBar;
    private JToolBar operationToolBar;
    private ToolBarButton addUrlButton;
    private ToolBarToggleButton clipboardSpyButton;
    private JXButton copyUrlToClipboardButton;
    private JXButton checkAvailableButton;
    private JXButton uploadButton;
    private JButton downloadButton;
    private JButton gotoWebPageButton;
// JFormDesigner - End of variables declaration  //GEN-END:variables

	/**
	 * @param trayEnable .
	 */
	public void setTrayEnabled(Boolean trayEnable) {
		if (trayEnable) {
			EventBus.publish(TrayInfoMessageBean.createInfoOK());
		} else {
			EventBus.publish(TrayInfoMessageBean.createWarn("неинициализирован"));
		}
	}

	/**
	 * Задача загрузки
	 */
	class UploadFileTask extends SwingWorker<String, Object> {
		private final File file;

		public UploadFileTask(File file) {
			this.file = file;
		}

		@Override
		public String doInBackground() {
			try {
				return connectionService.upload(file);
			} catch (IOException | InterruptedException e) {
				log.error(null, e);
			}
			return null;
		}

		@Override
		protected void done() {
			try {
				String uploadedUrl = get();
				EventQueue.invokeLater(() -> {
					if (isBlank(uploadedUrl)) {
						EventBus.publish(RFSInfoMessageBean.createError("Ошибка выгрузки файла " + file));
						return;
					}
					if (isNotBlank(uploadedUrl)) {
						StorageItem storageItem = new StorageItem();
//						storageItem.setUid(UUID.randomUUID().toString());
						storageItem.setUploadedDateTime(new Date());
						storageItem.setFileName(file.getName());
						String humanizeSize = humanize.Humanize.metricPrefix(FileUtils.sizeOf(file));
						storageItem.setSizeHumanize(humanizeSize);
						storageItem.setUrl(RFSHelper.view2FileUrl(uploadedUrl));

						storageItemEventList.add(storageItem);
						saveStorageItemsToDisk();
						storageItemsTable.packAll();

						EventBus.publish(RFSInfoMessageBean.createInfo("Выгружен в облако файл " + file));
					} else {
						EventBus.publish(RFSInfoMessageBean.createError("Ошибка выгрузки файла " + file));
					}
				});
			} catch (Exception e) {
				log.error("", e);
				EventBus.publish(RFSInfoMessageBean.createError(e.getMessage()));
			}
		}
	}

	/**
	 * Задача скачки
	 */
	class DownloadFileTask extends SwingWorker<Pair<Integer, String>, Object> {
		private final StorageItem storageItem;

		public DownloadFileTask(StorageItem storageItem) {
			this.storageItem = storageItem;
		}

		@Override
		public Pair<Integer, String> doInBackground() {
			if (storageItem != null) {
				try {
					return connectionService.download(storageItem);
				} catch (IOException | InterruptedException e) {
					log.error(null, e);
				}
			}
			return Pair.of(-1, "");
		}

		@Override
		protected void done() {
			try {
				Pair<Integer, String> pair = get();
				EventQueue.invokeLater(() -> {
					if (pair.getKey() <= 0) {
						EventBus.publish(RFSInfoMessageBean.createError("Ошибка загрузки " + storageItem.getUrl()));
					} else {
						EventBus.publish(RFSInfoMessageBean.createInfo("из облака загружен файл " + pair.getValue()));
					}
				});
			} catch (Exception e) {
				log.error("", e);
				EventBus.publish(RFSInfoMessageBean.createError(e.getMessage()));
			}
		}
	}

	/**
	 * Загрузить протокол
	 */
	public void loadStorageItemsFromDisk() {
		try {
			File jsonFile = Paths.get(applicationConfiguration.getString(JSON_PATH_KEY)).toFile();
			if (jsonFile.exists() && jsonFile.isFile() && jsonFile.canRead()) {

//				String body = IOUtils.toString(jsonFile.toURI(), StandardCharsets.UTF_8);
				Collection<StorageItem> storageItemCollection = objectMapper.readValue(jsonFile, new TypeReference<List<StorageItem>>() {
				});
				if (storageItemCollection == null) {
					EventBus.publish(InfoMessageBean.createError("Не могли прочитать: " + jsonFile));
				} else {
					storageItemEventList.addAll(storageItemCollection);
					storageItemsTable.packAll();
					EventBus.publish(InfoMessageBean.createInfo("Загрузка списка: " + storageItemEventList.size() + " строк"));
				}
			} else {
				log.error("Ошибка чтения {} isExist: {} isFile: {} canRead: {}", jsonFile, jsonFile.exists(), jsonFile.isFile(), jsonFile.canRead());
				EventBus.publish(InfoMessageBean.createError("Ошибка чтения: " + jsonFile));
			}
		} catch (Exception e) {
			log.error(null, e);
			EventBus.publish(InfoMessageBean.createError("Ошибка чтения: " + e.getMessage()));
		}
	}

	/**
	 * Сохранить протокол
	 */
	public void saveStorageItemsToDisk() {
		Collection<StorageItem> list = new ArrayList<StorageItem>(storageItemEventList);
		try {
			File jsonFile = Paths.get(applicationConfiguration.getString(JSON_PATH_KEY)).toFile();
			if (jsonFile.exists()) {
				if (jsonFile.isFile()) {
					if (jsonFile.canWrite()) {
						objectMapper.writeValue(jsonFile, list);
						EventBus.publish(InfoMessageBean.createInfo("Сохранение списка: " + list.size() + " строк"));
					} else {
						log.error("Ошибка доступа {} isExist: {} isFile: {} canWrite: {}", jsonFile, jsonFile.exists(), jsonFile.isFile(), jsonFile.canWrite());
						EventBus.publish(InfoMessageBean.createError("Ошибка записи: " + jsonFile));
					}
				} else {
					log.error("Ошибка записи {} isExist: {} isFile: {} canWrite: {}", jsonFile, jsonFile.exists(), jsonFile.isFile(), jsonFile.canWrite());
					EventBus.publish(InfoMessageBean.createError("Ошибка доступа: " + jsonFile));
				}
			} else {
				jsonFile.getParentFile().mkdirs();
				jsonFile.createNewFile();
			}
		} catch (Exception e) {
			log.error(null, e);
			EventBus.publish(InfoMessageBean.createError("Ошибка записи: " + e.getMessage()));
		}
	}

	@EventSubscriber(eventClass = ClipboardBodyBean.class)
	public void onCClipboardBodyBean(ClipboardBodyBean messageBean) {
		log.info("onClipboardBodyBean {}", messageBean);

		if (isNotBlank(messageBean.getBody())) {
			String body = messageBean.getBody();
			if (StringUtils.startsWith(body, "http")) {
				addStorageItemByUrl(body);
			} else {
				log.info("pass {}", StringUtils.substring(body, 0, 30) + "...");
			}
		} else {
			log.info("empty ClipboardBodyBean");
		}
	}
}
