package soup.utils.files;

import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class GifUtils {
    public static BufferedImage[] readGif(File file) throws IOException {

        ImageReader reader = ImageIO.getImageReadersBySuffix("GIF").next();
        ImageInputStream input = ImageIO.createImageInputStream(file);
        reader.setInput(input);
        BufferedImage[] images = new BufferedImage[reader.getNumImages(true)];
        for(int i = 0; i < reader.getNumImages(true);i++) {
            images[i] = reader.read(i);
        }

        return images;
    }
    public static ArrayList<BufferedImage> readGifAsList(File file) throws IOException {
        if(file == null){
            return null;
        }
        ImageReader reader = ImageIO.getImageReadersBySuffix("GIF").next();
        ImageInputStream input = ImageIO.createImageInputStream(file);
        reader.setInput(input);
        ArrayList<BufferedImage> images = new ArrayList<>();
        try {
            for(int i = 0; i < reader.getNumImages(true);i++) {
                images.add(reader.read(i));
            }
            return images;
        }catch (Exception e){

        }
        return null;
    }
    public static void writeGIF(BufferedImage[] images, int delay,File output) throws FileNotFoundException, IOException {
//        if(images.length < 1){
//            return;
//        }
        ImageOutputStream outputStream = new FileImageOutputStream(output);
        ImageWriter writer = ImageIO.getImageWritersBySuffix("gif").next();
        ImageWriteParam params = writer.getDefaultWriteParam();
        ImageTypeSpecifier imageTypeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(images[0].getType());
        IIOMetadata metadata = writer.getDefaultImageMetadata(imageTypeSpecifier, params);

        String metaFormatName = metadata.getNativeMetadataFormatName();
        IIOMetadataNode root = (IIOMetadataNode) metadata.getAsTree(metaFormatName);

        IIOMetadataNode graphicsControlExtensionNode = getNode(root, "GraphicControlExtension");
        graphicsControlExtensionNode.setAttribute("disposalMethod", "none");
        graphicsControlExtensionNode.setAttribute("userInputFlag", "FALSE");
        graphicsControlExtensionNode.setAttribute("transparentColorFlag", "FALSE");
        graphicsControlExtensionNode.setAttribute("delayTime", Integer.toString(delay));
        graphicsControlExtensionNode.setAttribute("transparentColorIndex", "0");

//        IIOMetadataNode commentsNode = getNode(root, "CommentExtensions");
//        commentsNode.setAttribute("CommentExtension", "from ISU AEROSPACE DEPT");

        IIOMetadataNode appExtensionsNode = getNode(root, "ApplicationExtensions");
        IIOMetadataNode child = new IIOMetadataNode("ApplicationExtension");
        child.setAttribute("applicationID", "NETSCAPE");
        child.setAttribute("authenticationCode", "2.0");

        int loopContinuously = 0;
        child.setUserObject(new byte[]{ 0x1, (byte) (loopContinuously & 0xFF), (byte) ((loopContinuously >> 8) & 0xFF)});
        appExtensionsNode.appendChild(child);
        metadata.setFromTree(metaFormatName, root);

        writer.setOutput(outputStream);
        writer.prepareWriteSequence(null);

        for(int i = 0; i < images.length;i++) {
            writer.writeToSequence(new IIOImage(images[i],null,metadata), params);
        }
        writer.endWriteSequence();
        outputStream.close();
    }


    private static IIOMetadataNode getNode(IIOMetadataNode rootNode, String nodeName){
        int nNodes = rootNode.getLength();
        for (int i = 0; i < nNodes; i++){
            if (rootNode.item(i).getNodeName().equalsIgnoreCase(nodeName)){
                return (IIOMetadataNode) rootNode.item(i);
            }
        }
        IIOMetadataNode node = new IIOMetadataNode(nodeName);
        rootNode.appendChild(node);
        return(node);
    }

}
