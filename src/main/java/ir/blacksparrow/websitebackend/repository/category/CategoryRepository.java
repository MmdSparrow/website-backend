package ir.blacksparrow.websitebackend.repository.category;

import ir.blacksparrow.websitebackend.business.dto.CategoryDto;
import ir.blacksparrow.websitebackend.dataModel.CategoryEntity;
import ir.blacksparrow.websitebackend.repository.ParentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@AllArgsConstructor
@Repository
public class CategoryRepository extends ParentRepository {
    private final ICategoryRepository categoryRepository;

    @Autowired
    public CategoryRepository(ModelMapper modelMapper, ICategoryRepository categoryRepository) {
        super(modelMapper);
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDto> findAll() {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        return mapList(categoryEntityList, CategoryDto.class);
    }

    public Optional<CategoryDto> getById(Long id) {
        try {
            CategoryEntity categoryEntity = categoryRepository.getById(id);
            return Optional.of(getModelMapper().map(categoryEntity, CategoryDto.class));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    public Optional<CategoryDto> insertAndUpdate(CategoryDto categoryDto) {
        CategoryEntity categoryEntity = getModelMapper().map(categoryDto, CategoryEntity.class);
        categoryEntity = categoryRepository.save(categoryEntity);
        return Optional.of(getModelMapper().map(categoryEntity, CategoryDto.class));
    }

    public List<CategoryDto> search(CategoryDto categoryDto) {
        List<CategoryEntity> categoryEntityList = categoryRepository.search(categoryDto.getCode(),
                categoryDto.getTitle());
        return mapList(categoryEntityList, CategoryDto.class);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }


}