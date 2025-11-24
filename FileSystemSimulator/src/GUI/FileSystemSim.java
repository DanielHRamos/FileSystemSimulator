/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import Disk.DiskSimulator;
import EDD.LinkedList;
import FileSystem.Directory;
import FileSystem.File;
import FileSystem.FileSystemManager;
import processs.ProcessManager;
import SchedulerManagement.CSCANScheduler;
import SchedulerManagement.FIFOScheduler;
import SchedulerManagement.SCANScheduler;
import SchedulerManagement.SSTFScheduler;
import java.util.Enumeration;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author Daniel
 */
public class FileSystemSim extends javax.swing.JFrame {

    private ProcessManager processManager;
    private FileSystemManager fs;
    private DiskPanel diskPanel;

    public FileSystemSim() {
        initComponents();

        DiskSimulator disk = new DiskSimulator(50);
        fs = new FileSystemManager(disk);
        diskPanel = new DiskPanel(disk);
        BlockAllocate.setLayout(new java.awt.BorderLayout());
        BlockAllocate.add(diskPanel, java.awt.BorderLayout.CENTER);
        processManager = new ProcessManager(new FIFOScheduler(), fs, 0, this);

        initFileExplorer();
    }

    private void initFileExplorer() {
        Directory rootDir = fs.getCurrentDir();
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(rootDir);
        buildTree(rootNode, rootDir);
        FileExplorer.setModel(new DefaultTreeModel(rootNode));
        FileExplorer.expandRow(0);

        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem createDirItem = new JMenuItem("Crear Directorio");
        createDirItem.addActionListener(e -> createDirectoryAction());
        popupMenu.add(createDirItem);

        JMenuItem createFileItem = new JMenuItem("Crear Archivo");
        createFileItem.addActionListener(e -> createFileAction());
        popupMenu.add(createFileItem);

        JMenuItem renameItem = new JMenuItem("Renombrar");
        renameItem.addActionListener(e -> renameNode());
        popupMenu.add(renameItem);

        JMenuItem deleteItem = new JMenuItem("Eliminar");
        deleteItem.addActionListener(e -> deleteNode());
        popupMenu.add(deleteItem);

        JMenuItem moveItem = new JMenuItem("Mover");
        moveItem.addActionListener(e -> moveNode());
        popupMenu.add(moveItem);

        FileExplorer.setComponentPopupMenu(popupMenu);

        FileExplorer.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    TreePath path = FileExplorer.getPathForLocation(e.getX(), e.getY());
                    if (path != null) {
                        FileExplorer.setSelectionPath(path);
                    }
                }
            }
        });
    }

    private void renameNode() {

        String mode = (String) ModeComboBox.getSelectedItem();
        if ("usuario".equalsIgnoreCase(mode)) {
            JOptionPane.showMessageDialog(this,
                    "Operación no permitida en modo usuario.",
                    "Acceso restringido",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) FileExplorer.getLastSelectedPathComponent();
        if (selectedNode == null) {
            return;
        }

        Object obj = selectedNode.getUserObject();
        String newName = JOptionPane.showInputDialog(this, "Nuevo nombre:");

        if (newName != null && !newName.trim().isEmpty()) {
            if (obj instanceof Directory dir) {
                dir.setName(newName);
            } else if (obj instanceof File file) {
                file.setName(newName);
            }
            ((DefaultTreeModel) FileExplorer.getModel()).nodeChanged(selectedNode);
        }
        refreshUI();
    }

    private void deleteNode() {

        String mode = (String) ModeComboBox.getSelectedItem();
        if ("usuario".equalsIgnoreCase(mode)) {
            JOptionPane.showMessageDialog(this,
                    "Operación no permitida en modo usuario.",
                    "Acceso restringido",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTreeModel model = (DefaultTreeModel) FileExplorer.getModel();
        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) FileExplorer.getLastSelectedPathComponent();
        if (selectedNode == null || selectedNode.isRoot()) {
            return;
        }

        Object obj = selectedNode.getUserObject();
        DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) selectedNode.getParent();
        Object parentObj = parentNode.getUserObject();

        if (parentObj instanceof Directory parentDir) {
            if (obj instanceof Directory dir) {
                parentDir.removeDirectory(dir);
            } else if (obj instanceof File file) {
                parentDir.removeFile(file);
                fs.getDisk().freeBlocks(file);
            }
            model.removeNodeFromParent(selectedNode);
            refreshUI();
        }
    }

    private void moveNode() {
        String mode = (String) ModeComboBox.getSelectedItem();
        if ("usuario".equalsIgnoreCase(mode)) {
            JOptionPane.showMessageDialog(this,
                    "Operación no permitida en modo usuario.",
                    "Acceso restringido",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) FileExplorer.getLastSelectedPathComponent();
        if (selectedNode == null || selectedNode.isRoot()) {
            return;
        }

        Object obj = selectedNode.getUserObject();

        String targetName = JOptionPane.showInputDialog(this,
                "Ingrese el nombre exacto del directorio destino:");

        if (targetName == null || targetName.trim().isEmpty()) {
            return;
        }

        DefaultMutableTreeNode root = (DefaultMutableTreeNode) FileExplorer.getModel().getRoot();
        DefaultMutableTreeNode targetNode = findNodeByName(root, targetName.trim());

        if (targetNode != null && targetNode.getUserObject() instanceof Directory targetDir) {
            DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) selectedNode.getParent();
            Object parentObj = parentNode.getUserObject();

            if (parentObj instanceof Directory parentDir) {
                if (obj instanceof Directory dir) {
                    parentDir.removeDirectory(dir);
                    dir.setParent(targetDir);
                    targetDir.addDirectory(dir);
                } else if (obj instanceof File file) {
                    parentDir.removeFile(file);
                    targetDir.addFile(file);
                }

                DefaultTreeModel model = (DefaultTreeModel) FileExplorer.getModel();
                model.removeNodeFromParent(selectedNode);
                model.insertNodeInto(selectedNode, targetNode, targetNode.getChildCount());
                FileExplorer.expandPath(new TreePath(targetNode.getPath()));
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se encontró un directorio con ese nombre.",
                    "Destino inválido",
                    JOptionPane.ERROR_MESSAGE);
        }
        refreshUI();
    }

    private DefaultMutableTreeNode findNodeByName(DefaultMutableTreeNode root, String name) {
        Enumeration<TreeNode> e = root.depthFirstEnumeration();
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
            Object obj = node.getUserObject();
            if (obj instanceof Directory dir && dir.getName().equals(name)) {
                return node;
            }
        }
        return null;
    }

    private void buildTree(DefaultMutableTreeNode parentNode, Directory dir) {

        LinkedList<Directory> subDirs = dir.getDirectories();
        LinkedList.Node<Directory> currentDir = subDirs.getHead();
        while (currentDir != null) {
            Directory sub = currentDir.getData();
            DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(sub);
            parentNode.add(childNode);
            buildTree(childNode, sub);
            currentDir = currentDir.next;
        }

        LinkedList<File> files = dir.getFiles();
        LinkedList.Node<File> currentFile = files.getHead();
        while (currentFile != null) {
            File f = currentFile.getData();
            DefaultMutableTreeNode fileNode = new DefaultMutableTreeNode(f);
            parentNode.add(fileNode);
            currentFile = currentFile.next;
        }
    }

    public void refreshFileExplorer() {
        Directory rootDir = fs.getCurrentDir();
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(rootDir.getName());
        buildTree(rootNode, rootDir);
        FileExplorer.setModel(new DefaultTreeModel(rootNode));
        FileExplorer.expandRow(0);
    }

    private void createDirectoryAction() {
        String mode = (String) ModeComboBox.getSelectedItem();
        if ("usuario".equalsIgnoreCase(mode)) {
            JOptionPane.showMessageDialog(this,
                    "Operación no permitida en modo usuario.",
                    "Acceso restringido",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTreeModel treeModel = (DefaultTreeModel) this.FileExplorer.getModel();
        DefaultMutableTreeNode selectedParentNode
                = (DefaultMutableTreeNode) this.FileExplorer.getLastSelectedPathComponent();

        if (selectedParentNode == null) {
            JOptionPane.showMessageDialog(this,
                    "Por favor seleccione un directorio donde quiere crear una carpeta.",
                    "Sin Selección",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Object nodeParentObject = selectedParentNode.getUserObject();

        if (nodeParentObject instanceof Directory) {
            String dirName = JOptionPane.showInputDialog(this,
                    "Ingrese el nombre del directorio:",
                    "Crear Directorio",
                    JOptionPane.QUESTION_MESSAGE);

            if (dirName != null && !dirName.trim().isEmpty()) {
                Directory parentDir = (Directory) nodeParentObject;

                Directory newDir = new Directory(dirName, parentDir);
                parentDir.addDirectory(newDir);

                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(newDir);
                treeModel.insertNodeInto(newNode, selectedParentNode, selectedParentNode.getChildCount());
                this.FileExplorer.expandPath(new TreePath(selectedParentNode.getPath()));

                refreshUI();
                System.out.println("Directorio creado exitosamente: " + dirName);
            }
        }
    }

    private void createFileAction() {

        String mode = (String) ModeComboBox.getSelectedItem();
        if ("usuario".equalsIgnoreCase(mode)) {
            JOptionPane.showMessageDialog(this,
                    "Operación no permitida en modo usuario.",
                    "Acceso restringido",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTreeModel treeModel = (DefaultTreeModel) this.FileExplorer.getModel();
        DefaultMutableTreeNode selectedParentNode
                = (DefaultMutableTreeNode) this.FileExplorer.getLastSelectedPathComponent();

        if (selectedParentNode == null) {
            JOptionPane.showMessageDialog(this,
                    "Por favor seleccione un directorio donde quiere crear un archivo.",
                    "Sin Selección",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Object nodeParentObject = selectedParentNode.getUserObject();

        if (nodeParentObject instanceof Directory) {
            String fileName = JOptionPane.showInputDialog(this,
                    "Ingrese el nombre del archivo:",
                    "Crear Archivo",
                    JOptionPane.QUESTION_MESSAGE);

            if (fileName != null && !fileName.trim().isEmpty()) {

                String sizeStr = JOptionPane.showInputDialog(this,
                        "Ingrese el tamaño del archivo en bloques:",
                        "Tamaño Archivo",
                        JOptionPane.QUESTION_MESSAGE);

                try {
                    int size = Integer.parseInt(sizeStr);

                    int startBlock = fs.getDisk().findFreeSpace(size);

                    if (startBlock != -1) {

                        File newFile = new File(fileName, size, startBlock, "usuario");

                        boolean allocationSuccess = fs.getDisk().allocateBlocks(newFile);

                        if (allocationSuccess) {

                            Directory parentDir = (Directory) nodeParentObject;
                            parentDir.addFile(newFile);

                            DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(newFile);
                            treeModel.insertNodeInto(newNode, selectedParentNode, selectedParentNode.getChildCount());
                            this.FileExplorer.expandPath(new TreePath(selectedParentNode.getPath()));

                            refreshUI();

                            System.out.println("Archivo creado exitosamente: " + fileName);
                        } else {
                            JOptionPane.showMessageDialog(this,
                                    "No hay espacio suficiente para este tamaño de archivo.",
                                    "Asignación fallida",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "No hay espacio suficiente en el disco.",
                                "Asignación fallida",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this,
                            "Por favor ingrese un número válido para el tamaño.",
                            "Tamaño inválido",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Los archivos solo pueden ser creados dentro de directorios.",
                    "Error al crear Archivo",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateFilesTable() {
        DefaultTableModel model = (DefaultTableModel) AllocationTable.getModel();
        model.setRowCount(0);

        if (fs != null && fs.getCurrentDir() != null) {

            EDD.LinkedList<File> files = fs.getCurrentDir().getFiles();
            EDD.LinkedList.Node<File> currentFile = files.getHead();

            while (currentFile != null) {
                File f = currentFile.getData();
                model.addRow(new Object[]{
                    f.getName(),
                    f.getSize(),
                    f.getStartBlock(),
                    f.getOwner()
                });
                currentFile = currentFile.next;
            }

            EDD.LinkedList<Directory> dirs = fs.getCurrentDir().getDirectories();
            EDD.LinkedList.Node<Directory> currentDir = dirs.getHead();

            while (currentDir != null) {
                Directory d = currentDir.getData();
                model.addRow(new Object[]{
                    d.getName(),
                    "-", "-", "-"
                });
                currentDir = currentDir.next;
            }
        }

        if (diskPanel != null) {
            diskPanel.repaint();
        }
    }

    private void addFilesRecursive(Directory dir, DefaultTableModel model) {
        
        EDD.LinkedList<File> files = dir.getFiles();
        EDD.LinkedList.Node<File> currentFile = files.getHead();
        while (currentFile != null) {
            File f = currentFile.getData();
            model.addRow(new Object[]{f.getName(), f.getSize(), f.getStartBlock(), f.getOwner()});
            currentFile = currentFile.next;
        }
        
        EDD.LinkedList<Directory> dirs = dir.getDirectories();
        EDD.LinkedList.Node<Directory> currentDir = dirs.getHead();
        while (currentDir != null) {
            addFilesRecursive(currentDir.getData(), model);
            currentDir = currentDir.next;
        }
    }

    private void updateAssignmentTable() {
        DefaultTableModel model = (DefaultTableModel) AllocationTable.getModel();
        model.setRowCount(0);

        if (fs != null && fs.getCurrentDir() != null) {
            addFilesRecursive(fs.getCurrentDir(), model);
        }
    }

    public void refreshUI() {
        updateFilesTable();
        updateAssignmentTable();
        diskPanel.repaint();
    }

    public void setFileSystemManager(FileSystemManager fs) {
        this.fs = fs;
    }

    public void setProcessManager(ProcessManager pm) {
        this.processManager = pm;
    }

    public FileSystemManager getFileSystemManager() {
        return fs;
    }

    public DiskPanel getDiskPanel() {
        return diskPanel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        FileExplorer = new javax.swing.JTree();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        AllocationTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        BlockAllocate = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        PolicyChooser = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        ModeComboBox = new javax.swing.JComboBox<>();
        jMenuBar1 = new javax.swing.JMenuBar();
        MenuFileButton = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel.setBackground(new java.awt.Color(255, 255, 255));
        jPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 153, 153));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Archivos");
        FileExplorer.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane1.setViewportView(FileExplorer);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 194, 510));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel1.setText("Explorador de archivos");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jPanel.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 210, 700));

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        AllocationTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Archivo", "Bloques Asignados", "Dirección Primer Bloque"
            }
        ));
        jScrollPane2.setViewportView(AllocationTable);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 470, 410));

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel2.setText("Tabla de Asignación");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jPanel.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 0, 490, 450));

        jPanel3.setBackground(new java.awt.Color(255, 255, 102));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel3.setText("Almacenamiento");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        javax.swing.GroupLayout BlockAllocateLayout = new javax.swing.GroupLayout(BlockAllocate);
        BlockAllocate.setLayout(BlockAllocateLayout);
        BlockAllocateLayout.setHorizontalGroup(
            BlockAllocateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 520, Short.MAX_VALUE)
        );
        BlockAllocateLayout.setVerticalGroup(
            BlockAllocateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 410, Short.MAX_VALUE)
        );

        jPanel3.add(BlockAllocate, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 520, 410));

        jPanel.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 0, 540, 450));

        jPanel5.setBackground(new java.awt.Color(102, 255, 102));
        jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel5.setText("Ajustes");
        jPanel5.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Modo:");
        jPanel5.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        PolicyChooser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FIFO", "SCAN", "C-SCAN", "SSTF" }));
        PolicyChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PolicyChooserActionPerformed(evt);
            }
        });
        jPanel5.add(PolicyChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 50, 130, -1));

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Política de planificación:");
        jPanel5.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        ModeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Usuario" }));
        ModeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModeComboBoxActionPerformed(evt);
            }
        });
        jPanel5.add(ModeComboBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, -1, -1));

        jPanel.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 450, 1030, 250));

        MenuFileButton.setText("Archivo");

        jMenuItem1.setText("Guardar");
        MenuFileButton.add(jMenuItem1);

        jMenuBar1.add(MenuFileButton);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 1240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 700, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void PolicyChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PolicyChooserActionPerformed
        String selectedPolicy = (String) PolicyChooser.getSelectedItem();

        switch (selectedPolicy) {
            case "FIFO":
                processManager.setScheduler(new FIFOScheduler());
                break;
            case "SSTF":
                processManager.setScheduler(new SSTFScheduler());
                break;
            case "SCAN":
                processManager.setScheduler(new SCANScheduler(true));
                break;
            case "C-SCAN":
                processManager.setScheduler(new CSCANScheduler());
                break;
        }

        System.out.println("Política seleccionada: " + selectedPolicy);
        updateAssignmentTable();
    }//GEN-LAST:event_PolicyChooserActionPerformed

    private void ModeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModeComboBoxActionPerformed

    }//GEN-LAST:event_ModeComboBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable AllocationTable;
    private javax.swing.JPanel BlockAllocate;
    private javax.swing.JTree FileExplorer;
    private javax.swing.JMenu MenuFileButton;
    private javax.swing.JComboBox<String> ModeComboBox;
    private javax.swing.JComboBox<String> PolicyChooser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
