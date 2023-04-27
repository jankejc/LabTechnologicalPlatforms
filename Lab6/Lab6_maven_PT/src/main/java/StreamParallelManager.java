import org.apache.commons.lang3.tuple.Pair;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamParallelManager
{/// POBRANIE RÓWNOLEGŁEGO STRUMIENIA Z KOLEKCJI
    public List<Path> collectFilenamesToCollection(String source)
    {
        List<Path> files = null;
        try (Stream<Path> stream = Files.list(Path.of(source))){
            files = stream.collect(Collectors.toList());
        } catch (IOException e) {}
        
        return files;
    }
    
    /// ODWZOROWANIE STRUMIENIA ŚCIEŻEK NA STRUMIEŃ PAR(NAZWA, ZDJĘCIE)
    public Stream<Pair<String, BufferedImage>> mapPathsToFilenameAndImagePairsParallel(List<Path> files)
    {
        Stream<Pair<String, BufferedImage>> inputPairsStream = files
                .stream()
                .parallel()
                .map(path -> {
                    String name = path.getFileName().toString();
                    BufferedImage image = null;
                    try
                    {
                        image = ImageIO.read(path.toFile());
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    Pair<String, BufferedImage> pair = Pair.of(name, image);
                    return pair;
                });
        
        return inputPairsStream;
    }
    
    /// ODWZOROWANIE STRUMIENIA PAR NA PARA(NAZWA, PRZEKSZTAŁCENIE)
    public Stream<Pair<String, BufferedImage>> mapFilenameAndImagePairsToFilenameAndEditedImagePairsParallel
    (Stream<Pair<String, BufferedImage>> inputPairsStream)
    {
        Stream<Pair<String, BufferedImage>> outputPairsStream = inputPairsStream
                .parallel()
                .map(pair -> {
                    BufferedImage original = pair.getRight();
                    BufferedImage image = new BufferedImage(original.getWidth(),
                            original.getHeight(),
                            original.getType());
                    
                    for (int i = 0; i < original.getWidth(); i++) {
                        for (int j = 0; j < original.getHeight(); j++) {
                            int rgb = original.getRGB(i, j);
                            Color color = new Color(rgb);
                            int red = color.getRed();
                            int blue = color.getBlue();
                            int green = color.getGreen();
                            Color outColor = new Color(red, blue, green);
                            int outRgb = outColor.getRGB();
                            image.setRGB(i, j, outRgb);
                        }
                    }
                    
                    Pair<String, BufferedImage> newPair = Pair.of(pair.getLeft(), image);
                    return newPair;
                });
        
        return outputPairsStream;
    }
    
    
    /// ZAPISANIE KAŻDEJ PARY DO KATALOGU OUTPUTIMAGES
    public void saveEditedImagesToOutputDir(Stream<Pair<String, BufferedImage>> outputPairsStream, String outputDir)
    {
        outputPairsStream.forEach(pair -> {
            try {
                // retrieve image
                File outputFile = new File(outputDir + "\\" + pair.getLeft());
                ImageIO.write(pair.getRight(), "jpg", outputFile);
            } catch (IOException e) {}
        });
    }
}
