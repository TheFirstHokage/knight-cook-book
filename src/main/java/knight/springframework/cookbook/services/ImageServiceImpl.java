package knight.springframework.cookbook.services;

import knight.springframework.cookbook.model.Recipe;
import knight.springframework.cookbook.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService{
    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }




    @Override
    @Transactional
    public void saveImageFile(Long id, MultipartFile file) {

        try {
            Recipe recipe = recipeRepository.findById(id).get();
            Byte[] bytes = new Byte[file.getBytes().length];
            int i=0;

            for (byte b : file.getBytes()){
                bytes[i++]= b;

            }
            recipe.setImage(bytes);
            recipeRepository.save(recipe);

        }catch (IOException e) {

        }


    }
}
