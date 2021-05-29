package alepoul;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import model.DaoCarrinho;

public class Carrinho extends JFrame {

	private JPanel contentPane;
	private JLabel lblStatus;
	private JTextField txtcodigo;
	private JTextField txtproduto;
	private JTextField txtquantidade;
	private JTextField txtvalor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Carrinho frame = new Carrinho();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Carrinho() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
			}
		});
		setTitle("Carrinho de Compras");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtCodigo = new JTextField();
		txtCodigo.setBounds(100, 28, 134, 20);
		contentPane.add(txtCodigo);
		txtCodigo.setColumns(10);

		JLabel lblNewLabel = new JLabel("Codigo");
		lblNewLabel.setBounds(10, 31, 46, 14);
		contentPane.add(lblNewLabel);

		txtproduto = new JTextField();
		txtproduto.setBounds(100, 73, 256, 20);
		contentPane.add(txtproduto);
		txtproduto.setColumns(10);

		txtquantidade = new JTextField();
		txtquantidade.setBounds(100, 115, 86, 20);
		contentPane.add(txtquantidade);
		txtquantidade.setColumns(10);

		txtvalor = new JTextField();
		txtvalor.setBounds(100, 159, 86, 20);
		contentPane.add(txtvalor);
		txtvalor.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("produto");
		lblNewLabel_1.setBounds(10, 76, 46, 14);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("quantidade");
		lblNewLabel_2.setBounds(10, 118, 67, 14);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("valor");
		lblNewLabel_3.setBounds(10, 162, 46, 14);
		contentPane.add(lblNewLabel_3);

		btnPesquisar = new JButton("");
		btnPesquisar.setBorder(null);
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selecionarcodigo();
			}
		});
		btnPesquisar.setEnabled(false);
		btnPesquisar.setToolTipText("Pesquisar Produto");
		btnPesquisar.setVerticalAlignment(SwingConstants.BOTTOM);
		btnPesquisar.setBackground(SystemColor.control);
		btnPesquisar.setForeground(Color.WHITE);
		btnPesquisar.setIcon(new ImageIcon(Carrinho.class.getResource("/icones/read.png")));
		btnPesquisar.setBounds(314, 14, 48, 48);
		contentPane.add(btnPesquisar);

		btnAdicionar = new JButton("");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAdicionar.setEnabled(false);
		btnAdicionar.setBorder(null);
		btnAdicionar.setToolTipText("Adicionar produto");
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setBackground(SystemColor.control);
		btnAdicionar.setIcon(new ImageIcon(Carrinho.class.getResource("/icones/create.png")));
		btnAdicionar.setBounds(45, 202, 56, 59);
		contentPane.add(btnAdicionar);

		btnAtualizar = new JButton("");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterarproduto();
			}
		});
		btnAtualizar.setEnabled(false);
		btnAtualizar.setToolTipText("Editar produto");
		btnAtualizar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAtualizar.setBorder(null);
		btnAtualizar.setIcon(new ImageIcon(Carrinho.class.getResource("/icones/update.png")));
		btnAtualizar.setBounds(124, 202, 50, 59);
		contentPane.add(btnAtualizar);

		btnDeletar = new JButton("");
		btnDeletar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deletarproduto();
			}
		});
		btnDeletar.setEnabled(false);
		btnDeletar.setToolTipText("Excluir produto");
		btnDeletar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDeletar.setBorder(null);
		btnDeletar.setIcon(new ImageIcon(Carrinho.class.getResource("/icones/delete.png")));
		btnDeletar.setBounds(209, 195, 56, 66);
		contentPane.add(btnDeletar);

		lblstatus = new JButton("");
		lblstatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		lblstatus.setBorder(null);
		lblstatus.setBackground(SystemColor.window);
		lblstatus.setForeground(Color.WHITE);
		lblstatus.setIcon(new ImageIcon(Carrinho.class.getResource("/icones/dberror.png")));
		lblstatus.setBounds(361, 202, 50, 50);
		contentPane.add(lblstatus);
	}
	// fim do construtor

//Criação de um objeto para acessar o método da classe DAO
	DaoCarrinho dao = new DaoCarrinho();
	private JButton btnPesquisar;
	private JButton btnAdicionar;
	private JButton btnAtualizar;
	private JButton btnDeletar;
	private JButton lblstatus;
	private JTextField txtCodigo;

	/**
	 * Status da conexão
	 */
	private void status() {
		try {
			// estabelecer uma conexão
			Connection con = dao.conectar();
			// status
			// System.out.println(con);
			// trocando o ícone do banco(status da conexão)
			if (con != null) {
				lblstatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/dbok.png")));
				btnPesquisar.setEnabled(true);
			} else {
				lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/dberror.png")));
			}
			// encerrar a conexão
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Selecionar produto
	 */
	private void selecionarcodigo() {
		
		// instrução sql para pesquisar um contato pelo cod
		String Pesquisar = "select * from carrinho where Codigo = ?";
		try {
			// estabelecer uma conexão
			Connection con = dao.conectar();
			// preparar a instrução sql
			PreparedStatement pst = con.prepareStatement(Pesquisar);
			// substituir o parâmetro (?) pelo nome do produto
			pst.setString(1, txtCodigo.getText());
			// resultado (obter os dados do contato pesquisado)
			ResultSet rs = pst.executeQuery();
			// se existir um contato correspondente
			if (rs.next()) {
				txtcodigo.setText(rs.getString(1)); // 1 -
				txtproduto.setText(rs.getString(2)); // 2 -
				txtquantidade.setText(rs.getString(3)); // 3 -
				txtvalor.setText(rs.getString(4)); // 4 -
				btnAtualizar.setEnabled(true);
				btnDeletar.setEnabled(true);

			} else {
				// Criar uma caixa de mensagem no Java
				JOptionPane.showMessageDialog(null, "Produto inexistente");
				btnAdicionar.setEnabled(true);
				btnPesquisar.setEnabled(false);
				btnPesquisar.setEnabled(false);
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	
	}

	/**
	 * Inserir um novo produto
	 */
	private void inserirproduto() {
		// validação dos campos
		if (txtproduto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "preencha o nome do produto");
		} else if (txtquantidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "preencha o fone do quantidade");
		} else if (txtproduto.getText().length() > 50) {
			JOptionPane.showMessageDialog(null, "o campo produto não pode ter mais que 50 caracteres");
		} else if (txtquantidade.getText().length() > 15) {
			JOptionPane.showMessageDialog(null, "o campo quantidade não pode ter mais que 15 caracteres");
		} else if (txtvalor.getText().length() > 50) {
			JOptionPane.showMessageDialog(null, "o campo valor não pode ter mais que 50 caracteres");
		}

		String create = "insert into produto (produto,quantidade,valor) values (?,?,?)";
		try {
			Connection con = dao.conectar();
			PreparedStatement pst = con.prepareStatement(create);
			pst.setString(1, txtproduto.getText());
			pst.setString(2, txtquantidade.getText());
			pst.setString(3, txtvalor.getText());
			// executar a query (insert no banco de dados)
			pst.executeUpdate();
			JOptionPane.showMessageDialog(null, "produto adicionado");
			con.close();
			limpar();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * editar contato
	 */
	private void alterarproduto() {
		// validação dos campos
		if (txtproduto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "preencha o nome do produto");
		} else if (txtquantidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "preencha a quantidade");
		} else if (txtproduto.getText().length() > 50) {
			JOptionPane.showMessageDialog(null, "o campo produto não pode ter mais que 50 caracteres");
		} else if (txtquantidade.getText().length() > 15) {
			JOptionPane.showMessageDialog(null, "o campo quantidade não pode ter mais que 15 caracteres");
		} else if (txtvalor.getText().length() > 50) {
			JOptionPane.showMessageDialog(null, "o campo valor não pode ter mais que 50 caracteres");
		} else {

			String Update = "Update produtos set produto=?,quantidade=?,valor=? where Codigo=?";
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(Update);
				pst.setString(1, txtproduto.getName());
				pst.setString(2, txtquantidade.getText());
				pst.setString(3, txtvalor.getText());
				pst.setString(4, txtCodigo.getText());
				// executar a query (insert no banco de dados)
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "contato editado com sucesso");
				con.close();
				limpar();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	/**
	 * EXCLUIR CONTATO CRUD Delete
	 */
	private void deletarproduto() {
		
		String delete = "delete from produto where Codigo=?";
		// confirmação de exclusão do produto
		int confirma = JOptionPane.showConfirmDialog(null, "confirma a exclusão deste Produto?", "Atenção!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			try {
				Connection con = dao.conectar();
				PreparedStatement pst = con.prepareStatement(delete);
				pst.setString(1, txtCodigo.getText());
				pst.executeUpdate();
				limpar();
				JOptionPane.showConfirmDialog(null, "Contato Produto");
				con.close();

			} catch (Exception e) {
				System.out.println();
			}
		} else {
			limpar();
		}

	}
	

	/**
	 * limpar campos e configurar botoes
	 */
	private void limpar() {
		// limpar os campos
		txtCodigo.setText(null);
		txtproduto.setText(null);
		txtquantidade.setText(null);
		txtvalor.setText(null);
		btnAdicionar.setEnabled(false);
		btnAtualizar.setEnabled(false);
		btnDeletar.setEnabled(false);
		btnPesquisar.setEnabled(true);
	}
}
