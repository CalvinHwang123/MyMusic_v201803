package calvin.baidumusic.view.widget.scrolllist;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import calvin.baidumusic.manager.PlayManager;

public class MyLrcList extends JList<String> {

	private static final long serialVersionUID = 1L;
	public Vector<String> datas = new Vector<>();
	public Timer timer = null;

	public MyLrcList(JScrollPane parent, int width, int height) {
		setPreferredSize(new Dimension(width, height));
		setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 36));
		setForeground(Color.WHITE);
		setFocusable(false);
		setBackground(new Color(0, 0, 0, 0));
		setOpaque(false);
		parent.getViewport().setOpaque(false);
		setSelectionBackground(new Color(0, 0, 0, 0));
		setListData(datas);
		setCellRenderer(new LRCRenderer());
	}

	public void initLrcList(List<Map<Long, String>> list) {
		if (list != null && list.size() > 0) {
			datas.clear();
			for (int i = 0; i < 13; i++) {
				datas.add(" ");
			}
			timer = new Timer(100, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					for (Map<Long, String> map : list) {
						for (Entry<Long, String> entry : map.entrySet()) {
							long delta = 0L;
							if (PlayManager.mp != null) {
								delta = PlayManager.mp.getCurrentTime();
							}
							if (datas.size() > 12) {
								datas.remove(datas.get(0));
							}
							if (delta != 0 && delta - entry.getKey() < 1) {
								if (!datas.contains(entry.getValue())) {
									datas.add(entry.getValue());
								}
								updateUI();
								return;
							}
						}
					}
				}
			});
		}
	}

	public void stop() {
		if (timer != null && timer.isRunning()) {
			timer.stop();
		}
	}
	
	public void startScroll() {
		if (timer != null) {
			timer.start();
		}
	}
	
	public void lrcUnavalible() {
		datas.clear();
		datas.add(" ");
		datas.add(" ");
		datas.add(" ");
		datas.add(" ");
		datas.add(" ");
		datas.add("ÔÝÎÞ¸è´Ê");
		datas.add("¼ÓÔØ¸ñÊ½ ¸èÃû-¸èÊÖ.lrc");
		updateUI();
	}
}
