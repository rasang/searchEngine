/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.net.itsearch.gui;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import crawler.SearchResultEntry;

/**
 *
 * @author 格格
 */
@SuppressWarnings("serial")
public class SearchResult extends javax.swing.JFrame {

	/**
	 * Creates new form searchResult
	 */
	public SearchResult() {
		initComponents();
	}

	public SearchResult(List<SearchResultEntry> list) {
		initComponents();
		esGuiSearch = new EsGuiSearch();
		resultList = getJpanelList(list);
		resultNum = list.size();
		pageNum = (resultList.size() + 1) / 2;
		currentPage = 1;
		displayResult();
	}

	private void displayResult() {
		resultJpanel.removeAll();
		resultJpanel.setLayout(new GridLayout(2, 1));
		resultJpanel.add(resultList.get(currentPage * 2 - 2));
		if (currentPage + currentPage <= resultNum) {
			resultJpanel.add(resultList.get(currentPage * 2 - 1));
		}
		resultJpanel.revalidate();
		resultJpanel.repaint();
		page.setText(currentPage + "/" + pageNum);
	}

	private void initComponents() {
		searchAgainButton = new javax.swing.JButton();
		resultJpanel = new javax.swing.JPanel();
		jumpLastPage = new javax.swing.JButton();
		jumpNextPage = new javax.swing.JButton();
		jumpChoosePage = new javax.swing.JButton();
		searchAgainBox = new javax.swing.JTextField();
		page = new javax.swing.JTextField();
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		searchAgainButton.setText("搜索");
		searchAgainButton.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				searchAgainButtonPerformed(evt);
			}
		});
		javax.swing.GroupLayout resultJpanelLayout = new javax.swing.GroupLayout(resultJpanel);
		resultJpanel.setLayout(resultJpanelLayout);
		resultJpanelLayout.setHorizontalGroup(resultJpanelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
		resultJpanelLayout.setVerticalGroup(resultJpanelLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 276, Short.MAX_VALUE));
		jumpLastPage.setText("上一页");
		jumpLastPage.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jumpLastPageActionPerformed(evt);
			}
		});
		jumpNextPage.setText("下一页");
		jumpNextPage.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jumpNextPageActionPerformed(evt);
			}
		});
		jumpChoosePage.setText("页数跳转");
		jumpChoosePage.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jumpChoosePageActionPerformed(evt);
			}
		});
		searchAgainBox.setText("");
		page.setText("1");
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				javax.swing.GroupLayout.Alignment.TRAILING,
				layout.createSequentialGroup().addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(page, javax.swing.GroupLayout.PREFERRED_SIZE, 42,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(18, 18, 18).addComponent(jumpLastPage)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jumpNextPage)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jumpChoosePage).addGap(12, 12, 12))
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(resultJpanel, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGroup(layout.createSequentialGroup()
										.addComponent(searchAgainBox, javax.swing.GroupLayout.PREFERRED_SIZE, 584,
												javax.swing.GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18)
										.addComponent(searchAgainButton, javax.swing.GroupLayout.PREFERRED_SIZE, 72,
												javax.swing.GroupLayout.PREFERRED_SIZE).addGap(0, 26, Short.MAX_VALUE)))
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addContainerGap()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(searchAgainBox, javax.swing.GroupLayout.PREFERRED_SIZE, 48,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addComponent(searchAgainButton, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)).addGap(27, 27, 27)
				.addComponent(resultJpanel, javax.swing.GroupLayout.PREFERRED_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
						.addComponent(jumpLastPage).addComponent(jumpNextPage).addComponent(jumpChoosePage).addComponent(page, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)).addContainerGap()));
		pack();
	}// </editor-fold>

	private void searchAgainButtonPerformed(java.awt.event.ActionEvent evt) {
		String queryString = searchAgainBox.getText();
		List<SearchResultEntry> list = esGuiSearch.fullTextSerch(queryString);
		if (list.isEmpty()) {
			JOptionPane.showMessageDialog(null, "未搜索到相关内容！");
		} else {
			resultList = getJpanelList(list);
			resultNum = list.size();
			pageNum = (resultList.size() + 1) / 2;
			currentPage = 1;
			displayResult();
		}

	}

	private void jumpLastPageActionPerformed(java.awt.event.ActionEvent evt) {
		if (currentPage == 1) {
			JOptionPane.showMessageDialog(null, "当前已为第一页，无法进入上一页！");
		} else {
			currentPage--;
			displayResult();
		}
	}

	private void jumpNextPageActionPerformed(java.awt.event.ActionEvent evt) {
		if (currentPage == pageNum) {
			JOptionPane.showMessageDialog(null, "当前已为最后一页，无法进入下一页！");
		} else {
			currentPage++;
			displayResult();
		}
	}

	private void jumpChoosePageActionPerformed(java.awt.event.ActionEvent evt) {
		int jumpPage = Integer.valueOf(page.getText());
		if (jumpPage >= 1 && jumpPage <= pageNum) {
			currentPage = jumpPage;
			displayResult();
		} else {
			JOptionPane.showMessageDialog(null, "输入页数不合法，请输入1-" + pageNum + "中的数字");
		}
	}

	private List<JPanel> getJpanelList(List<SearchResultEntry> list) {
		List<JPanel> resultList = new ArrayList<>();
		for (SearchResultEntry e : list) {
			JPanel jPanel = new SearchLook(e);
			resultList.add(jPanel);
		}
		return resultList;
	}

	private List<JPanel> resultList;
	private int pageNum;
	private int currentPage;
	private int resultNum;
	private EsGuiSearch esGuiSearch;

	private javax.swing.JButton jumpChoosePage;
	private javax.swing.JButton jumpLastPage;
	private javax.swing.JButton jumpNextPage;
	private javax.swing.JTextField page;
	private javax.swing.JPanel resultJpanel;
	private javax.swing.JTextField searchAgainBox;
	private javax.swing.JButton searchAgainButton;
	// End of variables declaration
}
