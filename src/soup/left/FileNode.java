package soup.left;

import java.io.File;

public class FileNode {
    public File file;

    public FileNode(File file){
        this.file = file;
    }
    public File getFile(){
        return file;
    }
    @Override
    public String toString() {
        return file.getName();
    }
}
