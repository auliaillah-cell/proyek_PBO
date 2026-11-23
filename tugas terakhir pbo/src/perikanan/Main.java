// Menentukan package tempat class Main berada.
package perikanan;

// Mengimpor class untuk menangkap keluaran teks dari class manajemen.
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

// Class Main menjadi jendela utama aplikasi desktop.
public class Main extends JFrame {

    // Palet warna tema agar seluruh tampilan konsisten.
    private static final Color LATAR_ATAS = new Color(7, 26, 43);
    private static final Color LATAR_BAWAH = new Color(15, 111, 124);
    private static final Color TEAL = new Color(56, 189, 248);
    private static final Color TEAL_TERANG = new Color(95, 212, 217);
    private static final Color EMAS = new Color(245, 158, 11);
    private static final Color MERAH = new Color(239, 68, 68);
    private static final Color TEKS_TERANG = new Color(242, 249, 252);
    private static final Color TEKS_REDUP = new Color(163, 190, 207);

    // Nama font yang dipakai; Java otomatis mencari pengganti bila tidak ada.
    private static final String FONT_UI = "Segoe UI";
    private static final String FONT_KODE = "Consolas";

    // Menyimpan objek yang mengelola data ikan dan transaksi.
    private final ManajemenIkan manajemenIkan = new ManajemenIkan();

    // Menampilkan hasil operasi di dalam jendela aplikasi.
    private final JTextArea areaOutput = new JTextArea();

    // Panel tengah yang dapat berisi output atau form input.
    private PanelKaca panelOutput;

    // Komponen output disimpan agar dapat dikembalikan setelah form ditutup.
    private JLabel judulOutput;
    private JScrollPane scrollOutput;

    // Referensi komponen animasi yang hanya mengubah presentasi.
    private PanelLatar panelUtama;
    private PanelKaca panelMenu;
    private SidebarPanel daftarMenu;
    private final Animator animator = new Animator();

    // Constructor untuk membangun seluruh tampilan GUI.
    public Main() {
        aturTampilanJendela();
        buatKomponenTampilan();
        tampilkanPesanAwal();
    }

    // Mengatur ukuran, judul, dan posisi jendela.
    private void aturTampilanJendela() {
        // Memberi nama yang tampil pada title bar aplikasi.
        setTitle("Sistem Informasi Manajemen Perikanan");
        // Menentukan ukuran awal jendela aplikasi.
        setSize(1100, 680);
        // Menentukan ukuran minimum agar komponen tetap terbaca.
        setMinimumSize(new Dimension(900, 580));
        // Menutup proses aplikasi ketika jendela ditutup.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Menempatkan jendela di tengah layar.
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                animator.dispose();
            }
        });
    }

    // Membuat header, tombol menu, dan area output.
    private void buatKomponenTampilan() {
        // Panel utama memakai latar gradasi dan cahaya lembut.
        panelUtama = new PanelLatar(new BorderLayout(18, 18));
        panelUtama.setBorder(BorderFactory.createEmptyBorder(20, 22, 20, 22));

        panelUtama.add(buatHeader(), BorderLayout.NORTH);
        panelOutput = buatPanelOutput();
        panelUtama.add(panelOutput, BorderLayout.CENTER);
        panelUtama.add(buatPanelMenu(), BorderLayout.WEST);

        setContentPane(panelUtama);
        SwingUtilities.invokeLater(this::mulaiAnimasiPembuka);
    }

    // Membuat judul aplikasi dengan garis aksen emas di sebelah kiri.
    private JPanel buatHeader() {
        JLabel judul = new JLabel("SISTEM INFORMASI MANAJEMEN PERIKANAN");
        judul.setForeground(Color.WHITE);
        judul.setFont(new Font(FONT_UI, Font.BOLD, 28));

        JLabel subJudul = new JLabel("Kelola data ikan, stok, harga, dan riwayat transaksi");
        subJudul.setForeground(TEKS_REDUP);
        subJudul.setFont(new Font(FONT_UI, Font.PLAIN, 14));

        JPanel teks = new JPanel(new GridLayout(2, 1, 0, 2));
        teks.setOpaque(false);
        teks.add(judul);
        teks.add(subJudul);

        // Garis vertikal bergradasi emas sebagai ornamen elegan.
        JComponent garisEmas = new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, EMAS, 0, getHeight(),
                        new Color(190, 120, 40)));
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 6, 6));
                g2.dispose();
            }
        };
        garisEmas.setPreferredSize(new Dimension(6, 52));

        JPanel header = new JPanel(new BorderLayout(14, 0));
        header.setOpaque(false);
        header.add(garisEmas, BorderLayout.WEST);
        header.add(teks, BorderLayout.CENTER);
        return header;
    }

    // Membuat panel output berlatar gelap seperti layar monitor.
    private PanelKaca buatPanelOutput() {
        areaOutput.setEditable(false);
        areaOutput.setFont(new Font(FONT_KODE, Font.PLAIN, 15));
        areaOutput.setForeground(TEKS_TERANG);
        areaOutput.setCaretColor(TEAL_TERANG);
        areaOutput.setOpaque(false);
        areaOutput.setLineWrap(false);
        areaOutput.setMargin(new Insets(10, 10, 10, 10));

        scrollOutput = new JScrollPane(areaOutput);
        scrollOutput.setBorder(BorderFactory.createEmptyBorder());
        scrollOutput.setOpaque(false);
        scrollOutput.getViewport().setOpaque(false);
        scrollOutput.getVerticalScrollBar().setUI(new ScrollBarTipis());
        scrollOutput.getHorizontalScrollBar().setUI(new ScrollBarTipis());
        scrollOutput.getVerticalScrollBar().setPreferredSize(new Dimension(12, 0));
        scrollOutput.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 12));

        judulOutput = new JLabel("HASIL OPERASI");
        judulOutput.setForeground(TEAL_TERANG);
        judulOutput.setFont(new Font(FONT_UI, Font.BOLD, 12));
        judulOutput.setBorder(BorderFactory.createEmptyBorder(0, 4, 8, 0));

        PanelKaca panel = new PanelKaca(new BorderLayout(),
                new Color(255, 255, 255, 38), new Color(255, 255, 255, 25), 24);
        panel.add(judulOutput, BorderLayout.NORTH);
        panel.add(scrollOutput, BorderLayout.CENTER);
        return panel;
    }

    // Panel menu berisi tujuh tombol operasi aplikasi.
    private PanelKaca buatPanelMenu() {
        JLabel judulMenu = new JLabel("MENU UTAMA", SwingConstants.LEFT);
        judulMenu.setForeground(EMAS);
        judulMenu.setFont(new Font(FONT_UI, Font.BOLD, 12));
        judulMenu.setBorder(BorderFactory.createEmptyBorder(0, 4, 10, 0));

        daftarMenu = new SidebarPanel();
        JPanel daftarTombol = daftarMenu;
        daftarTombol.setOpaque(false);

        tambahTombolMenu(daftarTombol, "Tambah Data Ikan", TEAL,
                this::tambahDataIkan);
        tambahTombolMenu(daftarTombol, "Tampilkan Data", TEAL,
                this::tampilkanDataIkan);
        tambahTombolMenu(daftarTombol, "Ubah Data", TEAL,
                this::ubahDataIkan);
        tambahTombolMenu(daftarTombol, "Hapus Data", TEAL,
                this::hapusDataIkan);
        tambahTombolMenu(daftarTombol, "Laporan", TEAL,
                this::tampilkanLaporan);
        tambahTombolMenu(daftarTombol, "Riwayat Transaksi", TEAL,
                this::tampilkanRiwayat);
        tambahTombolMenu(daftarTombol, "Keluar", MERAH,
                this::keluarAplikasi);

        panelMenu = new PanelKaca(new BorderLayout(),
                new Color(255, 255, 255, 43), new Color(255, 255, 255, 28), 24);
        panelMenu.setPreferredSize(new Dimension(265, 0));
        panelMenu.add(judulMenu, BorderLayout.NORTH);
        panelMenu.add(daftarTombol, BorderLayout.CENTER);
        return panelMenu;
    }

    // Menambahkan tombol 3D dengan warna dan aksi yang seragam.
    private void tambahTombolMenu(JPanel panel, String teks, Color warna,
                                  Runnable aksi) {
        Tombol3D tombol = new Tombol3D(teks, warna);
        tombol.setSidebar(true, panel == daftarMenu && panel.getComponentCount() == 0);
        tombol.addActionListener(event -> {
            if (panel instanceof SidebarPanel) {
                ((SidebarPanel) panel).pilih(tombol);
            }
            aksi.run();
        });
        panel.add(tombol);
        if (panel instanceof SidebarPanel && panel.getComponentCount() == 1) {
            ((SidebarPanel) panel).pilih(tombol);
        }
    }

    // Menampilkan pesan awal pada area output.
    private void tampilkanPesanAwal() {
        areaOutput.setText("Selamat datang di Sistem Informasi Manajemen Perikanan.\n"
                + "Silakan pilih menu di sebelah kiri untuk memulai.\n");
        animator.animate(250, Animator::easeOutCubic, t -> areaOutput.repaint(), null);
    }

    // Menampilkan form tambah data langsung di panel tengah aplikasi.
    private void tambahDataIkan() {
        JPanel form = panelForm("FORM TAMBAH DATA IKAN");
        JTextField fieldNama = new JTextField();
        JComboBox<String> pilihanJenis = new JComboBox<>(manajemenIkan.getDaftarJenisIkan());
        JTextField fieldHarga = new JTextField();
        JTextField fieldStok = new JTextField();
        JLabel pesan = labelPesanForm();

        tambahBarisForm(form, 0, "Nama ikan", fieldNama);
        tambahBarisForm(form, 1, "Jenis ikan", pilihanJenis);
        tambahBarisForm(form, 2, "Harga per kilogram", fieldHarga);
        tambahBarisForm(form, 3, "Stok (kg)", fieldStok);

        JButton simpan = tombolForm("Simpan", TEAL);
        simpan.addActionListener(event -> {
            String nama = fieldNama.getText().trim();
            if (nama.isEmpty()) {
                pesan.setText("Nama ikan wajib diisi.");
                return;
            }
            Double harga = angkaForm(fieldHarga, false, pesan);
            Double stok = angkaForm(fieldStok, true, pesan);
            if (harga == null || stok == null) {
                return;
            }
            int nomorJenis = pilihanJenis.getSelectedIndex() + 1;
            tampilkanHasil(() -> manajemenIkan.tambahIkan(
                    nama, nomorJenis, harga, stok));
        });

        JButton batal = tombolForm("Batal", MERAH);
        batal.addActionListener(event -> kembaliKeOutput());
        tambahTombolForm(form, simpan, batal, pesan);
        tampilkanForm(form, "FORM TAMBAH DATA IKAN");
    }

    // Menampilkan seluruh data ikan pada area output.
    private void tampilkanDataIkan() {
        tampilkanHasil(manajemenIkan::tampilkanIkan);
    }

    // Menampilkan form perubahan data langsung di panel tengah aplikasi.
    private void ubahDataIkan() {
        JPanel form = panelForm("FORM UBAH DATA IKAN");
        JTextField fieldId = new JTextField();
        JComboBox<String> pilihan = new JComboBox<>(new String[]{
            "Ubah Stok", "Ubah Harga", "Ubah Nama (semua data)"});
        JTextField fieldNama = new JTextField();
        JComboBox<String> pilihanJenis = new JComboBox<>(manajemenIkan.getDaftarJenisIkan());
        JTextField fieldHarga = new JTextField();
        JTextField fieldStok = new JTextField();
        JLabel pesan = labelPesanForm();

        tambahBarisForm(form, 0, "ID ikan", fieldId);
        tambahBarisForm(form, 1, "Jenis perubahan", pilihan);
        tambahBarisForm(form, 2, "Nama ikan", fieldNama);
        tambahBarisForm(form, 3, "Jenis ikan", pilihanJenis);
        tambahBarisForm(form, 4, "Harga per kilogram", fieldHarga);
        tambahBarisForm(form, 5, "Stok (kg)", fieldStok);

        Runnable aturField = () -> {
            boolean semua = pilihan.getSelectedIndex() == 2;
            fieldNama.setEnabled(semua);
            pilihanJenis.setEnabled(semua);
            fieldHarga.setEnabled(semua || pilihan.getSelectedIndex() == 1);
            fieldStok.setEnabled(semua || pilihan.getSelectedIndex() == 0);
        };
        pilihan.addActionListener(event -> aturField.run());
        aturField.run();

        JButton simpan = tombolForm("Simpan Perubahan", TEAL);
        simpan.addActionListener(event -> {
            Integer id = idForm(fieldId, pesan);
            if (id == null || manajemenIkan.cariIkanById(id) == null) {
                pesan.setText("ID ikan tidak ditemukan atau tidak valid.");
                return;
            }
            int mode = pilihan.getSelectedIndex();
            if (mode == 0) {
                Double stok = angkaForm(fieldStok, true, pesan);
                if (stok != null) {
                    tampilkanHasil(() -> manajemenIkan.ubahStokIkan(id, stok));
                }
            } else if (mode == 1) {
                Double harga = angkaForm(fieldHarga, false, pesan);
                if (harga != null) {
                    tampilkanHasil(() -> manajemenIkan.ubahHargaIkan(id, harga));
                }
            } else {
                String nama = fieldNama.getText().trim();
                Double harga = angkaForm(fieldHarga, false, pesan);
                Double stok = angkaForm(fieldStok, true, pesan);
                if (nama.isEmpty()) {
                    pesan.setText("Nama ikan wajib diisi.");
                } else if (harga != null && stok != null) {
                    int nomorJenis = pilihanJenis.getSelectedIndex() + 1;
                    tampilkanHasil(() -> manajemenIkan.ubahIkan(
                            id, nama, nomorJenis, harga, stok));
                }
            }
        });

        JButton batal = tombolForm("Batal", MERAH);
        batal.addActionListener(event -> kembaliKeOutput());
        tambahTombolForm(form, simpan, batal, pesan);
        tampilkanForm(form, "FORM UBAH DATA IKAN");
    }

    // Menampilkan form penghapusan data berdasarkan ID.
    private void hapusDataIkan() {
        JPanel form = panelForm("FORM HAPUS DATA IKAN");
        JTextField fieldId = new JTextField();
        JLabel pesan = labelPesanForm();
        tambahBarisForm(form, 0, "ID ikan", fieldId);

        Tombol3D hapus = new Tombol3D("HAPUS DATA IKAN", MERAH);
        hapus.setVisible(false);
        hapus.setFont(new Font(FONT_UI, Font.BOLD, 16));
        hapus.setPreferredSize(new Dimension(210, 50));
        hapus.addActionListener(event -> {
            Integer id = idForm(fieldId, pesan);
            if (id != null && manajemenIkan.cariIkanById(id) != null) {
                tampilkanHasil(() -> manajemenIkan.hapusIkan(id));
            } else if (id != null) {
                pesan.setText("ID ikan tidak ditemukan.");
            }
        });

        Tombol3D batal = new Tombol3D("BATAL", TEAL);
        batal.setFont(new Font(FONT_UI, Font.BOLD, 15));
        batal.setPreferredSize(new Dimension(150, 50));
        batal.addActionListener(event -> kembaliKeOutput());

        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        panelTombol.setOpaque(false);
        panelTombol.add(hapus);
        panelTombol.add(batal);
        GridBagConstraints tombolGbc = new GridBagConstraints();
        tombolGbc.gridx = 1;
        tombolGbc.gridy = 2;
        tombolGbc.anchor = GridBagConstraints.WEST;
        tombolGbc.insets = new Insets(14, 0, 8, 0);
        form.add(panelTombol, tombolGbc);

        GridBagConstraints pesanGbc = new GridBagConstraints();
        pesanGbc.gridx = 1;
        pesanGbc.gridy = 3;
        pesanGbc.anchor = GridBagConstraints.WEST;
        pesanGbc.insets = new Insets(4, 0, 0, 0);
        form.add(pesan, pesanGbc);

        // Tampilkan tombol hapus hanya saat ID yang diketik benar-benar ada.
        Runnable validasiId = () -> {
            String teksId = fieldId.getText().trim();
            boolean idDitemukan = false;
            if (!teksId.isEmpty()) {
                try {
                    int id = Integer.parseInt(teksId);
                    idDitemukan = id > 0 && manajemenIkan.cariIkanById(id) != null;
                } catch (NumberFormatException ex) {
                    idDitemukan = false;
                }
            }
            hapus.setVisible(idDitemukan);
            if (teksId.isEmpty()) {
                pesan.setText("Masukkan ID ikan yang ingin dihapus.");
            } else if (!idDitemukan) {
                pesan.setText("ID ikan tidak ditemukan.");
            } else {
                pesan.setText("Data ditemukan. Tekan Hapus Data untuk melanjutkan.");
            }
            form.revalidate();
            form.repaint();
        };
        fieldId.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent event) {
                validasiId.run();
            }

            @Override
            public void removeUpdate(DocumentEvent event) {
                validasiId.run();
            }

            @Override
            public void changedUpdate(DocumentEvent event) {
                validasiId.run();
            }
        });
        tampilkanForm(form, "FORM HAPUS DATA IKAN");
    }

    // Menampilkan laporan data ikan.
    private void tampilkanLaporan() {
        tampilkanHasil(manajemenIkan::tampilkanLaporan);
    }

    // Menampilkan riwayat transaksi.
    private void tampilkanRiwayat() {
        tampilkanHasil(manajemenIkan::tampilkanRiwayatTransaksi);
    }

    // Menutup aplikasi melalui tombol Keluar.
    private void keluarAplikasi() {
        int pilihan = JOptionPane.showConfirmDialog(
                this, "Apakah kamu yakin ingin keluar?", "Konfirmasi Keluar",
                JOptionPane.YES_NO_OPTION);
        if (pilihan == JOptionPane.YES_OPTION) {
            dispose();
            System.exit(0);
        }
    }

    // Membuat panel form transparan dengan judul bagian.
    private JPanel panelForm(String judulForm) {
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        form.setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));
        JLabel judul = new JLabel(judulForm);
        judul.setForeground(TEAL_TERANG);
        judul.setFont(new Font(FONT_UI, Font.BOLD, 18));

        GridBagConstraints judulGbc = new GridBagConstraints();
        judulGbc.gridx = 0;
        judulGbc.gridy = 0;
        judulGbc.gridwidth = 2;
        judulGbc.anchor = GridBagConstraints.WEST;
        judulGbc.insets = new Insets(0, 0, 18, 0);
        form.add(judul, judulGbc);
        return form;
    }

    // Menambahkan satu label dan satu komponen input ke form.
    private void tambahBarisForm(JPanel form, int baris, String teks,
                                 JComponent komponen) {
        GridBagConstraints labelGbc = new GridBagConstraints();
        labelGbc.gridx = 0;
        labelGbc.gridy = baris + 1;
        labelGbc.anchor = GridBagConstraints.WEST;
        labelGbc.insets = new Insets(5, 0, 5, 18);

        JLabel label = new JLabel(teks);
        label.setForeground(TEKS_TERANG);
        label.setFont(new Font(FONT_UI, Font.BOLD, 14));
        form.add(label, labelGbc);

        GridBagConstraints inputGbc = new GridBagConstraints();
        inputGbc.gridx = 1;
        inputGbc.gridy = baris + 1;
        inputGbc.weightx = 1;
        inputGbc.fill = GridBagConstraints.HORIZONTAL;
        inputGbc.insets = new Insets(5, 0, 5, 0);
        komponen.setPreferredSize(new Dimension(300, 34));
        komponen.setFont(new Font(FONT_UI, Font.PLAIN, 14));
        komponen.setForeground(Color.WHITE);
        if (komponen instanceof JTextField) {
            ((JTextField) komponen).setCaretColor(TEAL_TERANG);
        }
        komponen.setOpaque(false);
        komponen.setBorder(new BorderInputKaca());
        form.add(komponen, inputGbc);
    }

    // Membuat label untuk menampilkan validasi form tanpa dialog input.
    private JLabel labelPesanForm() {
        JLabel pesan = new JLabel(" ");
        pesan.setForeground(new Color(255, 170, 170));
        pesan.setFont(new Font(FONT_UI, Font.PLAIN, 13));
        return pesan;
    }

    // Menambahkan tombol simpan/batal di bagian bawah form.
    private void tambahTombolForm(JPanel form, JButton tombolUtama,
                                  JButton tombolBatal, JLabel pesan) {
        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelTombol.setOpaque(false);
        panelTombol.add(tombolUtama);
        panelTombol.add(tombolBatal);

        // Letakkan tombol tepat setelah baris input terakhir agar tetap terlihat.
        int barisTombol = (form.getComponentCount() - 1) / 2 + 1;
        GridBagConstraints tombolGbc = new GridBagConstraints();
        tombolGbc.gridx = 0;
        tombolGbc.gridy = barisTombol;
        tombolGbc.gridwidth = 2;
        tombolGbc.anchor = GridBagConstraints.WEST;
        tombolGbc.insets = new Insets(18, 0, 8, 0);
        form.add(panelTombol, tombolGbc);

        GridBagConstraints pesanGbc = new GridBagConstraints();
        pesanGbc.gridx = 0;
        pesanGbc.gridy = barisTombol + 1;
        pesanGbc.gridwidth = 2;
        pesanGbc.anchor = GridBagConstraints.WEST;
        form.add(pesan, pesanGbc);
    }

    // Membuat tombol sederhana untuk aksi form.
    private JButton tombolForm(String teks, Color warna) {
        // Menggunakan tombol 3D agar warna dan tulisan selalu terlihat jelas.
        Tombol3D tombol = new Tombol3D(teks, warna);
        tombol.setFont(new Font(FONT_UI, Font.BOLD, 15));
        tombol.setPreferredSize(new Dimension(170, 50));
        return tombol;
    }

    // Menampilkan form pada panel tengah aplikasi.
    private void tampilkanForm(JPanel form, String judulForm) {
        panelOutput.removeAll();
        JLabel judul = new JLabel(judulForm);
        judul.setForeground(TEAL_TERANG);
        judul.setFont(new Font(FONT_UI, Font.BOLD, 12));
        judul.setBorder(BorderFactory.createEmptyBorder(0, 4, 8, 0));
        panelOutput.add(judul, BorderLayout.NORTH);
        panelOutput.add(form, BorderLayout.CENTER);
        panelOutput.revalidate();
        panelOutput.repaint();
        transisiKonten(null);
    }

    // Mengembalikan panel tengah ke tampilan hasil operasi.
    private void kembaliKeOutput() {
        panelOutput.removeAll();
        JLabel judul = new JLabel("HASIL OPERASI");
        judul.setForeground(TEAL_TERANG);
        judul.setFont(new Font(FONT_UI, Font.BOLD, 12));
        judul.setBorder(BorderFactory.createEmptyBorder(0, 4, 8, 0));
        panelOutput.add(judul, BorderLayout.NORTH);
        panelOutput.add(scrollOutput, BorderLayout.CENTER);
        panelOutput.revalidate();
        panelOutput.repaint();
        transisiKonten(null);
    }

    // Mengubah teks field menjadi angka dengan validasi harga atau stok.
    private Double angkaForm(JTextField field, boolean bolehNol, JLabel pesan) {
        try {
            double angka = Double.parseDouble(field.getText().trim().replace(',', '.'));
            boolean valid = !Double.isNaN(angka) && !Double.isInfinite(angka)
                    && (bolehNol ? angka >= 0 : angka > 0);
            if (valid) {
                return angka;
            }
        } catch (NumberFormatException ex) {
            // Pesan validasi ditampilkan pada bagian bawah form.
        }
        pesan.setText(bolehNol
                ? "Stok harus berupa angka nol atau lebih."
                : "Harga harus berupa angka lebih besar dari 0.");
        return null;
    }

    // Mengubah teks field menjadi ID positif dengan validasi.
    private Integer idForm(JTextField field, JLabel pesan) {
        try {
            int id = Integer.parseInt(field.getText().trim());
            if (id > 0) {
                return id;
            }
        } catch (NumberFormatException ex) {
            // Pesan validasi ditampilkan pada bagian bawah form.
        }
        pesan.setText("ID harus berupa angka lebih besar dari 0.");
        return null;
    }

    // Menjalankan operasi manajemen dan menampilkan hasilnya pada GUI.
    private void tampilkanHasil(Runnable operasi) {
        // Hasil operasi menggantikan isi output sebelumnya.
        kembaliKeOutput();
        areaOutput.setText(tangkapKeluaran(operasi));
        // Menggulung kembali ke baris pertama agar hasil terbaca dari atas.
        areaOutput.setCaretPosition(0);
        mulaiAnimasiOutput();
    }

    // Menangkap output lama dari ManajemenIkan agar tidak muncul di terminal.
    private String tangkapKeluaran(Runnable operasi) {
        PrintStream keluaranLama = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        try {
            // Mengalihkan System.out sementara ke buffer di memori.
            System.setOut(new PrintStream(buffer));
            operasi.run();
            // Mengambil teks hasil operasi untuk ditampilkan di GUI.
            return buffer.toString();
        } finally {
            // Mengembalikan System.out setelah operasi selesai.
            System.setOut(keluaranLama);
        }
    }

    // Menampilkan pesan singkat kepada pengguna melalui dialog.
    private void tampilkanPesan(String pesan) {
        JOptionPane.showMessageDialog(this, pesan, "Informasi",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // Mencampur dua warna dengan perbandingan t (0 = a, 1 = b).
    private static Color campur(Color a, Color b, float t) {
        int r = Math.round(a.getRed() + (b.getRed() - a.getRed()) * t);
        int g = Math.round(a.getGreen() + (b.getGreen() - a.getGreen()) * t);
        int bl = Math.round(a.getBlue() + (b.getBlue() - a.getBlue()) * t);
        return new Color(r, g, bl);
    }

    // Menyalakan penghalusan tepi agar gambar tampak mulus.
    private static void haluskan(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_PURE);
    }

    // Menjalankan animasi pembuka berurutan dengan jeda stagger kecil.
    private void mulaiAnimasiPembuka() {
        if (panelUtama == null) {
            return;
        }
        panelOutput.setAlpha(0f);
        panelOutput.setOffset(0, 20);
        if (panelMenu != null) {
            panelMenu.setAlpha(0f);
            panelMenu.setOffset(-30, 0);
        }
        animator.animate(400, Animator::easeOutCubic, t -> {
            panelOutput.setAlpha(t);
            panelOutput.setOffset(0, Math.round(20 * (1f - t)));
        }, () -> animator.animate(400, Animator::easeOutCubic, t -> {
            if (panelMenu != null) {
                panelMenu.setAlpha(t);
                panelMenu.setOffset(Math.round(-30 * (1f - t)), 0);
            }
        }, null));
        animator.loop(20000, t -> panelUtama.repaint());
    }

    // Menganimasikan pertukaran visual konten tanpa menyentuh data aplikasi.
    private void mulaiAnimasiKonten(Component komponenBaru) {
        panelOutput.setAlpha(0.72f);
        panelOutput.setOffset(0, 8);
        animator.animate(320, Animator::easeOutCubic, t -> {
            panelOutput.setAlpha(0.72f + 0.28f * t);
            panelOutput.setOffset(0, Math.round(8 * (1f - t)));
        }, null);
    }

    // Memberi fade singkat saat hasil operasi baru ditampilkan.
    private void mulaiAnimasiOutput() {
        animator.animate(250, Animator::easeOutCubic, t -> {
            panelOutput.setAlpha(0.72f + 0.28f * t);
            areaOutput.repaint();
        }, null);
    }

    // Mengubah isi panel melalui callback visual dengan transisi ringan.
    private void transisiKonten(Runnable gantiIsi) {
        if (gantiIsi != null) {
            gantiIsi.run();
        }
        mulaiAnimasiKonten(panelOutput);
    }

    // Border transparan dengan ring cyan ketika field sedang fokus.
    private static class BorderInputKaca extends AbstractBorder {
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y,
                                int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            haluskan(g2);
            boolean fokus = c.isFocusOwner();
            g2.setColor(fokus ? new Color(56, 189, 248, 220)
                    : new Color(255, 255, 255, 120));
            g2.setStroke(new java.awt.BasicStroke(fokus ? 2f : 1f));
            g2.draw(new RoundRectangle2D.Float(x + 1, y + 1, width - 3,
                    height - 3, 12, 12));
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(7, 12, 7, 12);
        }
    }

    // Panel menu yang menggambar satu pill aktif di bawah tombol.
    private static class SidebarPanel extends JPanel {
        SidebarPanel() {
            super(new GridLayout(7, 1, 0, 9));
            setOpaque(false);
        }

        // Memindahkan pill aktif dengan easing overshoot.
        void pilih(Component tombol) {
            for (Component komponen : getComponents()) {
                if (komponen instanceof Tombol3D) {
                    ((Tombol3D) komponen).setMenuAktif(komponen == tombol);
                }
            }
            repaint();
        }
    }

    // Mesin animasi ringan berbasis satu timer Swing bersama di EDT.
    private static class Animator {
        private static final List<Animasi> ANIMASI = new ArrayList<>();
        private static final Timer TIMER = new Timer(16, e -> tick());

        // Menambahkan animasi satu kali ke daftar bersama.
        void animate(long durasi, Easing easing, Update update, Runnable selesai) {
            ANIMASI.add(new Animasi(durasi, easing, update, selesai, false));
            if (!TIMER.isRunning()) {
                TIMER.start();
            }
        }

        // Menambahkan animasi berulang untuk drift latar yang hemat repaint.
        void loop(long durasi, Update update) {
            ANIMASI.add(new Animasi(durasi, Animator::easeInOutCubic, update, null, true));
            if (!TIMER.isRunning()) {
                TIMER.start();
            }
        }

        // Menghentikan timer saat jendela dibuang.
        void dispose() {
            ANIMASI.clear();
            TIMER.stop();
        }

        // Memajukan semua animasi pada Event Dispatch Thread.
        private static void tick() {
            long sekarang = System.currentTimeMillis();
            Iterator<Animasi> iterator = ANIMASI.iterator();
            while (iterator.hasNext()) {
                Animasi animasi = iterator.next();
                float mentah = Math.min(1f, (sekarang - animasi.mulai) /
                        (float) animasi.durasi);
                animasi.update.run(animasi.easing.apply(mentah));
                if (mentah >= 1f) {
                    if (animasi.berulang) {
                        animasi.mulai = sekarang;
                    } else {
                        iterator.remove();
                        if (animasi.selesai != null) {
                            animasi.selesai.run();
                        }
                    }
                }
            }
            if (ANIMASI.isEmpty()) {
                TIMER.stop();
            }
        }

        // Easing cubic untuk gerakan keluar yang lembut.
        static float easeOutCubic(float t) {
            return 1f - (float) Math.pow(1f - t, 3);
        }

        // Easing cubic untuk gerakan masuk-keluar yang seimbang.
        static float easeInOutCubic(float t) {
            return t < 0.5f ? 4f * t * t * t
                    : 1f - (float) Math.pow(-2f * t + 2f, 3) / 2f;
        }

        // Easing overshoot kecil untuk efek pill yang terasa seperti spring.
        static float easeOutBack(float t) {
            float c1 = 1.70158f;
            float c3 = c1 + 1f;
            return 1f + c3 * (float) Math.pow(t - 1f, 3)
                    + c1 * (float) Math.pow(t - 1f, 2);
        }

        // Fungsi pembaruan nilai animasi.
        private interface Update { void run(float t); }

        // Fungsi easing yang memetakan waktu menjadi nilai animasi.
        private interface Easing { float apply(float t); }

        // Data internal satu animasi.
        private static class Animasi {
            private final long durasi;
            private final Easing easing;
            private final Update update;
            private final Runnable selesai;
            private final boolean berulang;
            private long mulai = System.currentTimeMillis();

            Animasi(long durasi, Easing easing, Update update,
                    Runnable selesai, boolean berulang) {
                this.durasi = durasi;
                this.easing = easing;
                this.update = update;
                this.selesai = selesai;
                this.berulang = berulang;
            }
        }
    }

    // Latar jendela: gradasi gelap dengan cahaya teal lembut di pojok.
    private static class PanelLatar extends JPanel {
        PanelLatar(BorderLayout layout) {
            super(layout);
            setOpaque(true);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            haluskan(g2);
            int w = getWidth();
            int h = getHeight();

            // Gradasi diagonal dari gelap ke biru laut.
            g2.setPaint(new GradientPaint(0, 0, LATAR_ATAS, w, h, LATAR_BAWAH));
            g2.fillRect(0, 0, w, h);

            // Cahaya teal lembut yang bergerak perlahan mengikuti waktu.
            float waktu = (System.currentTimeMillis() % 20000L) / 20000f;
            float radius = Math.max(w, h) * 0.7f;
            g2.setPaint(new RadialGradientPaint(new Point2D.Float(
                    w * (0.15f + 0.06f * (float) Math.sin(waktu * Math.PI * 2)),
                    h * (0.12f + 0.05f * (float) Math.cos(waktu * Math.PI * 2))),
                    radius, new float[]{0f, 1f},
                    new Color[]{new Color(95, 212, 217, 58), new Color(95, 212, 217, 0)}));
            g2.fillRect(0, 0, w, h);

            // Cahaya cyan dan emas tambahan sebagai tiga glow blob besar.
            g2.setPaint(new RadialGradientPaint(new Point2D.Float(
                    w * (0.82f + 0.08f * (float) Math.cos(waktu * Math.PI * 2)),
                    h * (0.38f + 0.08f * (float) Math.sin(waktu * Math.PI * 2))),
                    radius * 0.65f, new float[]{0f, 1f},
                    new Color[]{new Color(56, 189, 248, 48), new Color(56, 189, 248, 0)}));
            g2.fillRect(0, 0, w, h);

            // Cahaya emas tipis di kanan bawah yang juga bergeser perlahan.
            g2.setPaint(new RadialGradientPaint(new Point2D.Float(w, h),
                    radius * 0.6f, new float[]{0f, 1f},
                    new Color[]{new Color(245, 158, 11, 38), new Color(245, 158, 11, 0)}));
            g2.fillRect(0, 0, w, h);

            g2.dispose();
        }
    }

    // Panel kaca: sudut membulat, gradasi, bayangan berlapis, dan garis cahaya.
    private static class PanelKaca extends JPanel {
        private final Color warnaAtas;
        private final Color warnaBawah;
        private final int lengkung;
        private float alpha = 1f;
        private int offsetX;
        private int offsetY;

        PanelKaca(BorderLayout layout, Color warnaAtas, Color warnaBawah, int lengkung) {
            super(layout);
            this.warnaAtas = warnaAtas;
            this.warnaBawah = warnaBawah;
            this.lengkung = lengkung;
            setOpaque(false);
            // Ruang kanan dan bawah ekstra dipakai untuk bayangan.
            setBorder(BorderFactory.createEmptyBorder(16, 16, 24, 24));
        }

        // Mengatur opacity dan pergeseran visual panel saat transisi.
        void setAlpha(float alpha) {
            this.alpha = Math.max(0f, Math.min(1f, alpha));
            repaint();
        }

        // Mengatur offset visual panel untuk efek slide.
        void setOffset(int x, int y) {
            offsetX = x;
            offsetY = y;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            haluskan(g2);
            Composite komposit = g2.getComposite();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.translate(offsetX, offsetY);
            int w = getWidth() - 8;
            int h = getHeight() - 8;

            // Bayangan berlapis memberi kesan panel melayang.
            for (int i = 0; i < 6; i++) {
                g2.setColor(new Color(0, 0, 0, 24));
                g2.fill(new RoundRectangle2D.Float(2 + i, 3 + i, w, h, lengkung, lengkung));
            }

            // Badan panel dengan gradasi vertikal.
            Shape badan = new RoundRectangle2D.Float(0, 0, w, h, lengkung, lengkung);
            g2.setPaint(new GradientPaint(0, 0, warnaAtas, 0, h, warnaBawah));
            g2.fill(badan);

            // Kilau tipis di bagian atas panel.
            g2.setClip(badan);
            g2.setPaint(new GradientPaint(0, 0, new Color(255, 255, 255, 38),
                    0, h * 0.35f, new Color(255, 255, 255, 0)));
            g2.fillRect(0, 0, w, (int) (h * 0.35f));
            g2.setClip(null);

            // Garis tepi teal tipis dan garis cahaya di dalamnya.
            g2.setStroke(new java.awt.BasicStroke(1.6f));
            g2.setColor(new Color(255, 255, 255, 125));
            g2.draw(new RoundRectangle2D.Float(0.8f, 0.8f, w - 1.6f, h - 1.6f,
                    lengkung, lengkung));
            g2.setStroke(new java.awt.BasicStroke(1f));
            g2.setColor(new Color(255, 255, 255, 35));
            g2.draw(new RoundRectangle2D.Float(3, 3, w - 6, h - 6,
                    lengkung - 4, lengkung - 4));

            g2.setComposite(komposit);
            g2.dispose();
        }
    }

    // Tombol 3D: sisi tebal, kilau kaca, efek hover, dan efek tertekan.
    private static class Tombol3D extends JButton {
        private static final int TEBAL = 6;
        private final Color warnaDasar;
        private boolean disorot = false;
        private boolean sidebar;
        private boolean aktif;

        Tombol3D(String teks, Color warnaDasar) {
            super(teks);
            this.warnaDasar = warnaDasar;
            setFont(new Font(FONT_UI, Font.BOLD, 15));
            setForeground(Color.WHITE);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            // Mengubah tampilan saat kursor masuk atau keluar dari tombol.
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    disorot = true;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    disorot = false;
                    repaint();
                }
            });
        }

        // Menandai tombol sebagai item sidebar yang memakai pill bersama.
        void setSidebar(boolean sidebar, boolean aktif) {
            this.sidebar = sidebar;
            this.aktif = aktif;
            if (sidebar) {
                setFont(new Font(FONT_UI, Font.BOLD, 14));
            }
        }

        // Mengubah status aktif tombol tanpa mengubah aksi navigasinya.
        void setMenuAktif(boolean aktif) {
            this.aktif = aktif;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            haluskan(g2);

            int w = getWidth();
            int h = getHeight();
            if (sidebar) {
                // Setiap item mengecat kapsul aktif pada seluruh bounds tombolnya.
                if (aktif) {
                    g2.setColor(new Color(56, 189, 248, 55));
                    g2.fillRoundRect(1, 3, w - 2, h - 3, 15, 15);
                    g2.setPaint(new GradientPaint(0, 0,
                            new Color(56, 189, 248, 230), 0, h,
                            new Color(14, 165, 233, 215)));
                    g2.fillRoundRect(1, 0, w - 2, h - 5, 15, 15);
                    g2.setColor(new Color(255, 255, 255, 75));
                    g2.drawRoundRect(1, 0, w - 3, h - 6, 15, 15);
                } else {
                    // Latar gelap tetap terlihat pada tombol yang tidak aktif,
                    // sehingga tombol tidak tampak putih dan teks lebih jelas.
                    g2.setPaint(new GradientPaint(0, 0,
                            new Color(8, 52, 72, disorot ? 230 : 205),
                            0, h, new Color(4, 31, 49, disorot ? 245 : 225)));
                    g2.fillRoundRect(1, 0, w - 2, h - 5, 15, 15);
                    g2.setColor(new Color(120, 225, 235, disorot ? 190 : 110));
                    g2.drawRoundRect(1, 0, w - 3, h - 6, 15, 15);
                }
                g2.setFont(getFont());
                g2.setColor(new Color(248, 253, 255));
                g2.drawString(getText(), 16 + (disorot ? 4 : 0),
                        (h + g2.getFontMetrics().getAscent()
                                - g2.getFontMetrics().getDescent()) / 2);
                g2.dispose();
                return;
            }
            boolean ditekan = getModel().isArmed() && getModel().isPressed();

            // Ukuran muka tombol; sisa tinggi dipakai untuk sisi tebal dan bayangan.
            int lebar = w - 6;
            int tinggi = h - TEBAL - 4;
            int x = 3;
            int turun = ditekan ? TEBAL - 2 : 0;
            int lengkung = 16;

            // Warna muka menjadi lebih terang saat kursor berada di atas tombol.
            Color dasar = disorot ? campur(warnaDasar, Color.WHITE, 0.14f) : warnaDasar;
            Color atas = campur(dasar, Color.WHITE, 0.22f);
            Color bawah = campur(dasar, Color.BLACK, 0.18f);
            Color sisi = campur(warnaDasar, Color.BLACK, 0.52f);

            // Bayangan lembut di bawah tombol.
            if (!ditekan) {
                for (int i = 0; i < 4; i++) {
                    g2.setColor(new Color(0, 0, 0, 28));
                    g2.fill(new RoundRectangle2D.Float(x + 1 - i / 2f, TEBAL + 3 + i / 2f,
                            lebar - 2 + i, tinggi, lengkung, lengkung));
                }
            }

            // Sisi tebal tombol yang menciptakan kesan 3D.
            g2.setColor(sisi);
            g2.fill(new RoundRectangle2D.Float(x, TEBAL, lebar, tinggi, lengkung, lengkung));

            // Muka tombol dengan gradasi vertikal.
            Shape muka = new RoundRectangle2D.Float(x, turun, lebar, tinggi, lengkung, lengkung);
            g2.setPaint(new GradientPaint(0, turun, atas, 0, turun + tinggi, bawah));
            g2.fill(muka);

            // Kilau kaca pada separuh bagian atas muka tombol.
            g2.setClip(muka);
            g2.setPaint(new GradientPaint(0, turun, new Color(255, 255, 255, 90),
                    0, turun + tinggi / 2f, new Color(255, 255, 255, 0)));
            g2.fillRect(x, turun, lebar, tinggi / 2);
            g2.setClip(null);

            // Garis tepi cahaya tipis di sekeliling muka tombol.
            g2.setStroke(new java.awt.BasicStroke(1.2f));
            g2.setColor(new Color(255, 255, 255, 110));
            g2.draw(new RoundRectangle2D.Float(x + 0.6f, turun + 0.6f, lebar - 1.2f,
                    tinggi - 1.2f, lengkung, lengkung));

            // Teks di tengah muka tombol dengan bayangan agar tajam terbaca.
            g2.setFont(getFont());
            FontMetrics fm = g2.getFontMetrics();
            int tx = x + (lebar - fm.stringWidth(getText())) / 2;
            int ty = turun + (tinggi + fm.getAscent() - fm.getDescent()) / 2;
            g2.setColor(new Color(0, 0, 0, 90));
            g2.drawString(getText(), tx, ty + 1);
            g2.setColor(getForeground());
            g2.drawString(getText(), tx, ty);

            g2.dispose();
        }
    }

    // Scrollbar tipis berbentuk kapsul agar serasi dengan tema gelap.
    private static class ScrollBarTipis extends BasicScrollBarUI {
        @Override
        protected void configureScrollBarColors() {
            thumbColor = new Color(34, 177, 166, 190);
            trackColor = new Color(255, 255, 255, 12);
        }

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return tombolKosong();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return tombolKosong();
        }

        // Menghilangkan tombol panah bawaan scrollbar.
        private JButton tombolKosong() {
            JButton tombol = new JButton();
            tombol.setPreferredSize(new Dimension(0, 0));
            tombol.setMinimumSize(new Dimension(0, 0));
            tombol.setMaximumSize(new Dimension(0, 0));
            return tombol;
        }

        @Override
        protected void paintTrack(Graphics g, JComponent c, java.awt.Rectangle area) {
            Graphics2D g2 = (Graphics2D) g.create();
            haluskan(g2);
            g2.setColor(trackColor);
            g2.fillRoundRect(area.x + 2, area.y + 2, area.width - 4, area.height - 4, 8, 8);
            g2.dispose();
        }

        @Override
        protected void paintThumb(Graphics g, JComponent c, java.awt.Rectangle area) {
            if (area.isEmpty() || !scrollbar.isEnabled()) {
                return;
            }
            Graphics2D g2 = (Graphics2D) g.create();
            haluskan(g2);
            g2.setColor(isThumbRollover() ? TEAL_TERANG : thumbColor);
            g2.fillRoundRect(area.x + 2, area.y + 2, area.width - 4, area.height - 4, 8, 8);
            g2.dispose();
        }
    }

    // Mengatur font dialog bawaan agar serasi dengan tema aplikasi.
    private static void aturFontDialog() {
        Font fontDialog = new Font(FONT_UI, Font.PLAIN, 14);
        UIManager.put("OptionPane.messageFont", fontDialog);
        UIManager.put("OptionPane.buttonFont", new Font(FONT_UI, Font.BOLD, 13));
        UIManager.put("TextField.font", fontDialog);
        UIManager.put("ComboBox.font", fontDialog);
    }

    // Menjalankan GUI pada Event Dispatch Thread Swing.
    public static void main(String[] args) {
        try {
            // Menggunakan tema tampilan sesuai sistem operasi.
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            // Tampilan default Swing tetap digunakan jika tema sistem gagal dipasang.
        }

        // Font dialog diatur setelah tema sistem dipasang.
        aturFontDialog();

        // Memulai pembuatan jendela pada thread khusus Swing.
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
