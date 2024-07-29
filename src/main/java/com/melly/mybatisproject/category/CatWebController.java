package com.melly.mybatisproject.category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/catweb")
public class CatWebController {
    @Autowired
    private CategoryServiceImpl categoryService;

    @GetMapping("")
    public String indexHome() {
        return "cat"; // resources/templates/* 에서부터 index[.html] 찾는다.
    }

    @GetMapping("/catweb/category_list")    // 브라우저의 URL 주소
    public String categoryList(Model model, @RequestParam String name, @RequestParam int page) {
        try {
            if (name == null) {
                name = "";
            }
//            List<ICategory> allList = this.categoryService.getAllList();
            SearchCategoryDto searchCategoryDto = SearchCategoryDto.builder()
                    .name(name).page(page).build();
            int total = this.categoryService.countAllByNameContains(searchCategoryDto);
            searchCategoryDto.setTotal(total);
            List<ICategory> allList = this.categoryService.findAllByNameContains(searchCategoryDto);
            model.addAttribute("allList", allList);
            model.addAttribute("searchCategoryDto", searchCategoryDto);
//            java 에서 html 문자를 만드는 고전적인 방법은 매우 안 좋다.
//            String sPages = this.getHtmlPageString(searchCategoryDto);
//            model.addAttribute("pageHtml", sPages);
        } catch (Exception ex) {
            log.error(ex.toString());
            model.addAttribute("error_message", "오류가 발생했습니다. 관리자에게 문의하세요.");
            return "error/error_save";  // resources/templates 폴더안의 화면파일
        }
        return "catweb/category_list";  // resources/templates 폴더안의 화면파일
    }

    @GetMapping("/catweb/category_listView")    // 브라우저의 URL 주소
    public String categoryListView(Model model, @RequestParam Long id) {
        try {
            if ( id == null || id <= 0 ) {
                model.addAttribute("error_message", "ID는 1보다 커야 합니다.");
                return "error/error_bad";  // resources/templates 폴더안의 화면파일
            }
            ICategory find = this.categoryService.findById(id);
            if ( find == null ) {
                model.addAttribute("error_message", id + " 데이터가 없습니다.");
                return "error/error_find";
            }
            model.addAttribute("allList", find);
        } catch (Exception ex) {
            log.error(ex.toString());
            model.addAttribute("error_message", "서버 에러입니다. 관리자에게 문의 하세요.");
            return "error/error_save";  // resources/templates 폴더안의 화면파일
        }
        return "catweb/category_listView";  // resources/templates 폴더안의 화면파일
    }

    @PostMapping("/catweb/category_list_update")
    public String categoryListUpdate(Model model, @ModelAttribute CategoryDto categoryDto) {
        try {
            if (categoryDto == null || categoryDto.getId() <= 0 || categoryDto.getName().isEmpty()) {
                model.addAttribute("error_message", "id는 1보다 커야하고, name 이 있어야 합니다.");
                return "error/error_bad";  // resources/templates 폴더안의 화면파일
            }
            ICategory find = this.categoryService.findById(categoryDto.getId());
            if (find == null) {
                model.addAttribute("error_message", categoryDto.getId() + " 데이터가 없습니다.");
                return "error/error_find";
            }
            this.categoryService.update(categoryDto.getId(), categoryDto);
        } catch (Exception ex) {
            log.error(ex.toString());
            model.addAttribute("error_message", categoryDto.getName() + " 중복입니다.");
            return "error/error_save";  // resources/templates 폴더안의 화면파일
        }
        return "redirect:category_list?page=1&name=";
    }

    @GetMapping("/catweb/category_list_delete")
    public String categoryListDelete(Model model, @RequestParam Long id) {
        try {
            if (id == null || id <= 0) {
                model.addAttribute("error_message", "id는 1보다 커야 합니다.");
                return "error/error_bad";  // resources/templates 폴더안의 화면파일
            }
            ICategory find = this.categoryService.findById(id);
            if (find == null) {
                model.addAttribute("error_message", id + " 데이터가 없습니다.");
                return "error/error_find";
            }
            this.categoryService.delete(id);
        } catch (Exception ex) {
            log.error(ex.toString());
            model.addAttribute("error_message", "서버 에러입니다. 관리자에게 문의 하세요.");
            return "error/error_save";  // resources/templates 폴더안의 화면파일
        }
        return "redirect:category_list?page=1&name=";
    }

    @GetMapping("/catweb/catweb/category_insertView")    // 브라우저의 URL 주소
    public String categoryListInsertPage(Model model, @RequestParam Long id) {
        try {
            if ( id == null || id <= 0 ) {
                model.addAttribute("error_message", "ID는 1보다 커야 합니다.");
                return "error/error_bad";  // resources/templates 폴더안의 화면파일
            }
            ICategory find = this.categoryService.findById(id);
            if ( find == null ) {
                model.addAttribute("error_message", id + " 데이터가 없습니다.");
                return "error/error_find";
            }
            model.addAttribute("allList", find);
        } catch (Exception ex) {
            log.error(ex.toString());
            model.addAttribute("error_message", "서버 에러입니다. 관리자에게 문의 하세요.");
            return "error/error_save";  // resources/templates 폴더안의 화면파일
        }
        return "/catweb/catweb/category_insertView";  // resources/templates 폴더안의 화면파일
    }

    @PostMapping("/catweb/catweb/category_insert")
    public String categoryListInsert(Model model, @ModelAttribute CategoryDto categoryDto) {
        try {
            if (categoryDto == null || categoryDto.getId() <= 0 || categoryDto.getName().isEmpty()) {
                model.addAttribute("error_message", "id는 1보다 커야하고, name 이 있어야 합니다.");
                return "error/error_bad";  // resources/templates 폴더안의 화면파일
            }
            ICategory find = this.categoryService.findById(categoryDto.getId());
            if (find == null) {
                model.addAttribute("error_message", categoryDto.getId() + " 데이터가 없습니다.");
                return "error/error_find";
            }
            this.categoryService.update(categoryDto.getId(), categoryDto);
        } catch (Exception ex) {
            log.error(ex.toString());
            model.addAttribute("error_message", categoryDto.getName() + " 중복입니다.");
            return "error/error_save";  // resources/templates 폴더안의 화면파일
        }
        return "redirect:category_list?page=1&name=";
    }


    @PostMapping("/catweb/categoryListSearch")
    public String categoryListSearch(Model model, @ModelAttribute CategoryDto categoryDto) {
        try {
            if (categoryDto == null || categoryDto.getId() <= 0 || categoryDto.getName().isEmpty()) {
                model.addAttribute("error_message", "id는 1보다 커야하고, name 이 있어야 합니다.");
                return "error/error_bad";  // resources/templates 폴더안의 화면파일
            }
            ICategory find = this.categoryService.findById(categoryDto.getId());
            if (find == null) {
                model.addAttribute("error_message", categoryDto.getId() + " 데이터가 없습니다.");
                return "error/error_find";
            }
            this.categoryService.update(categoryDto.getId(), categoryDto);
        } catch (Exception ex) {
            log.error(ex.toString());
            model.addAttribute("error_message", categoryDto.getName() + " 중복입니다.");
            return "error/error_save";  // resources/templates 폴더안의 화면파일
        }
        return "category_list?page=1&name=";
    }
}
