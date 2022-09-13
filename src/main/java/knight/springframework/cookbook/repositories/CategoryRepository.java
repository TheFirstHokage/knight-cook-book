package knight.springframework.cookbook.repositories;

import knight.springframework.cookbook.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category,Long> {

Optional<Category> findByDescription(String description);




}
