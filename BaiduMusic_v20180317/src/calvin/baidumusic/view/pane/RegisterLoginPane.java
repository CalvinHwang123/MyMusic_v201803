package calvin.baidumusic.view.pane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import calvin.baidumusic.bean.User;
import calvin.baidumusic.client.ClientThread;
import calvin.baidumusic.manager.FrameManager;
import calvin.baidumusic.manager.PaneManager;
import calvin.baidumusic.test.MyTest;
import calvin.baidumusic.util.MD5Util;
import calvin.baidumusic.view.widget.dialog.MessageDialog;
import net.sf.json.JSONObject;

public class RegisterLoginPane extends JPanel implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private final int WIDTH = 265;
	private final int HEIGHT = 295;
	
	private JTextField uNameEdit = null;
	private JPasswordField uPwdEdit = null;
	private JPasswordField uPwdConfirm = null;
	private JButton loginBtn = null;
	private JLabel register = null;
	private JLabel forgetPwd = null;
	
	private JLabel hint = null;
	
	private boolean login = false;
	private boolean forget = false;
	private LoginPane parent = null;
	
	public RegisterLoginPane(LoginPane parent, boolean login, boolean forget) {
		this.parent = parent;
		this.login = login;
		this.forget = forget;
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.WHITE);
		setLayout(null);
		JLabel userLogin = new JLabel();
		if (forget) {
			userLogin.setText("重置密码");
		} else {
			if (login) {
				userLogin.setText("用户登录");
			} else {
				userLogin.setText("用户注册");
			}
		}
		
		userLogin.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		userLogin.setBounds(30, 30, 75, 20);
		add(userLogin);
		
		uNameEdit = new JTextField("用户名/邮箱/手机号");
		uNameEdit.setBounds(30, 75, 210, 30);
		uNameEdit.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		add(uNameEdit);
		uNameEdit.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (!uNameEdit.getText().equals("用户名/邮箱/手机号")
						&& !uNameEdit.getText().equals("")) {
					return;
				}
				uNameEdit.setText("用户名/邮箱/手机号");
			}
			@Override
			public void focusGained(FocusEvent e) {
				uNameEdit.setText("");
			}
		});
		uNameEdit.setFocusable(false);
		uNameEdit.addMouseListener(this);
		
		uPwdEdit = new JPasswordField("");
		uPwdEdit.setBounds(30, 110, 210, 30);
		uPwdEdit.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		add(uPwdEdit);
		
		if (!login) {
			uPwdConfirm = new JPasswordField("");
			uPwdConfirm.setBounds(30, 145, 210, 30);
			uPwdConfirm.setFont(new Font("微软雅黑", Font.PLAIN, 12));
			add(uPwdConfirm);
		}
		
		loginBtn = new JButton();
		if (forget) {
			loginBtn.setText("提交");
		} else {
			if (login) {
				loginBtn.setText("登录");
			} else {
				loginBtn.setText("注册");
			}
		}
		
		loginBtn.setBounds(30, 210, 210, 40);
		loginBtn.setFont(new Font("微软雅黑", Font.BOLD, 16));
		loginBtn.setBackground(new Color(63, 165, 255));
		loginBtn.setForeground(Color.WHITE);
		loginBtn.setBorder(BorderFactory.createEmptyBorder());
		loginBtn.setFocusable(false);
		add(loginBtn);
		
		register = new JLabel("立即注册");
		register.setBounds(30, 260, 50, 12);
		register.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		register.setForeground(new Color(63, 165, 255));
		add(register);
		
		forgetPwd = new JLabel("忘记密码");
		forgetPwd.setBounds(190, 260, 50, 12);
		forgetPwd.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		forgetPwd.setForeground(new Color(63, 165, 255));
		add(forgetPwd);
		
		hint = new JLabel("");
		hint.setBounds(30, 186, 210, 13);
		hint.setHorizontalAlignment(JLabel.CENTER);
		hint.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		hint.setForeground(Color.RED);
		add(hint);
		
		loginBtn.addActionListener(this);
		register.addMouseListener(this);
		forgetPwd.addMouseListener(this);
	}

	public JLabel getHint() {
		return hint;
	}

	public void setHint(JLabel hint) {
		this.hint = hint;
	}

	public JTextField getuNameEdit() {
		return uNameEdit;
	}

	public void setuNameEdit(JTextField uNameEdit) {
		this.uNameEdit = uNameEdit;
	}

	public JPasswordField getuPwdEdit() {
		return uPwdEdit;
	}

	public void setuPwdEdit(JPasswordField uPwdEdit) {
		this.uPwdEdit = uPwdEdit;
	}

	public JPasswordField getuPwdConfirm() {
		return uPwdConfirm;
	}

	public void setuPwdConfirm(JPasswordField uPwdConfirm) {
		this.uPwdConfirm = uPwdConfirm;
	}

	public JButton getLoginBtn() {
		return loginBtn;
	}

	public void setLoginBtn(JButton loginBtn) {
		this.loginBtn = loginBtn;
	}

	public JLabel getRegister() {
		return register;
	}

	public void setRegister(JLabel register) {
		this.register = register;
	}

	public JLabel getForgetPwd() {
		return forgetPwd;
	}

	public void setForgetPwd(JLabel forgetPwd) {
		this.forgetPwd = forgetPwd;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		String uName = uNameEdit.getText();
		@SuppressWarnings("deprecation")
		String uPwd = uPwdEdit.getText();
		if (obj == loginBtn) {
			if (forget) {
				@SuppressWarnings("deprecation")
				String uPwdConf = uPwdConfirm.getText();
				if (uName.equals("")
						|| uPwd.equals("")
						|| uPwdConf.equals("")) {
					hint.setText("用户名或密码为空！");
					return;
				}
				if (!uPwdConf.equals(uPwd)) {
					hint.setText("两次密码不一致！");
					return;
				}
				requestResetPwd(uName, uPwd);
//				hint.setText("loading...");
				hint.setText("");
			} else {
				if (login) {
					if (uName.equals("")
							|| uPwd.equals("")) {
						hint.setText("用户名或密码为空！");
						return;
					}
					requestLogin(uName, uPwd);
//					hint.setText("loading...");
					hint.setText("");
				} else {
					@SuppressWarnings("deprecation")
					String uPwdConf = uPwdConfirm.getText();
					if (uName.equals("")
							|| uPwd.equals("")
							|| uPwdConf.equals("")) {
						hint.setText("用户名或密码为空！");
						return;
					}
					if (!uPwdConf.equals(uPwd)) {
						hint.setText("两次密码不一致！");
						return;
					}
					requestRegister(uName, uPwd);
//					hint.setText("loading...");
					hint.setText("");
				}
			}
		}
	}

	private void requestResetPwd(String uName, String uPwd) {
		try {
			String newPwd = MD5Util.EncoderByMd5(uPwd);
			System.out.println("uname="+uName+",uPwd="+newPwd);
			
			JSONObject jo = new JSONObject();
			jo.put("type", "reset_pwd");
			jo.put("user", new User(uName, newPwd));
			System.out.println(jo.toString());
			if (MyTest.ct == null) {
				new MessageDialog("服务器未开启");
				return;
			} 
			try {
				MyTest.ct.getClientStream().send(jo.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private void requestRegister(String uName, String uPwd) {
		try {
			String newPwd = MD5Util.EncoderByMd5(uPwd);
			System.out.println("uname="+uName+",uPwd="+newPwd);
			
			JSONObject jo = new JSONObject();
			jo.put("type", "register");
			jo.put("user", new User(uName, newPwd));
			System.out.println(jo.toString());
			if (MyTest.ct == null) {
				new MessageDialog("服务器未开启");
				return;
			} 
			try {
				MyTest.ct.getClientStream().send(jo.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	private void requestLogin(String uName, String uPwd) {
		try {
			String newPwd = MD5Util.EncoderByMd5(uPwd);
			System.out.println("uname="+uName+",uPwd="+newPwd);
			
			JSONObject jo = new JSONObject();
			jo.put("type", "login");
			jo.put("user", new User(uName, newPwd));
			System.out.println(jo.toString());
			if (MyTest.ct == null) {
				new MessageDialog("服务器未开启");
				return;
			}
			if (MyTest.ct != null && !PaneManager.getHeadPaneInstance(FrameManager.getMainFrameInstance()).socketClose) {
				try {
					MyTest.ct.getClientStream().send(jo.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (PaneManager.getHeadPaneInstance(FrameManager.getMainFrameInstance()).socketClose) {
				// 重新登录
				try {
					MyTest.ct = new ClientThread(new Socket("localhost", 10000));
					MyTest.ct.start();
					MyTest.ct.getClientStream().send(jo.toString());
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object obj = e.getSource();
		if (obj == register) {
			parent.dispose();
			HeadPane.loginPane = new LoginPane(false, false);
		} else if (obj == forgetPwd) {
			parent.dispose();
			HeadPane.loginPane = new LoginPane(false, true);
		} else if (obj == uNameEdit) {
			uNameEdit.setFocusable(true);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}