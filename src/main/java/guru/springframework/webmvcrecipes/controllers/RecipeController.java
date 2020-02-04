package guru.springframework.webmvcrecipes.controllers;

import guru.springframework.webmvcrecipes.commands.ErrorCommand;
import guru.springframework.webmvcrecipes.commands.RecipeCommand;
import guru.springframework.webmvcrecipes.exceptions.NotFoundException;
import guru.springframework.webmvcrecipes.services.CategoryService;
import guru.springframework.webmvcrecipes.services.ImageService;
import guru.springframework.webmvcrecipes.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class RecipeController {
    private RecipeService recipeService;
    private ImageService imageService;
    private ServletContext servletContext;
    private ResourceLoader resourceLoader;
    private final CategoryService categoryService;

    @Value("classpath:/static/images/guacamole400x400WithX.jpg")
    private Resource defaultImage;

    public RecipeController(RecipeService recipeService, ImageService imageService,
                            ServletContext servletContext, ResourceLoader resourceLoader, CategoryService categoryService) {
        this.recipeService = recipeService;
        this.imageService = imageService;
        this.servletContext = servletContext;
        this.resourceLoader = resourceLoader;
        this.categoryService = categoryService;
    }

    @RequestMapping("/{id}/show")
    public String getRecipeById(@PathVariable String id, Model model) {
        Long longID = Long.valueOf(id);
        model.addAttribute("recipe", this.recipeService.findById(longID));

        return "recipe/show";
    }

    @GetMapping(value = "/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }

    @GetMapping(value = "/{id}/update")
    public String updateRecipe(@PathVariable String id,  Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        model.addAttribute("categories", categoryService.getAllCategoryCommands());

        return "recipe/recipeform";
    }

    @PostMapping(name = "/")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command,
                               @RequestParam(value = "cats", required = false) String[] categoryIds) {
        for (String categoryId : categoryIds) {
            command.getCategories().add(categoryService.findCommandById(Long.valueOf(categoryId)));
        }

        RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);
    //Object commandCopy = command;


        return "redirect:/recipe/" + savedCommand.getId() + "/show";
        //return "redirect:/recipe/1/show";
    }

    @GetMapping(value = "/{id}/delete")
    public String deleteRecipe(@PathVariable String id) {

        recipeService.deleteById(Long.valueOf(id));
        log.debug("Deleting recipe " + id);
        return "redirect:/";
    }

    @GetMapping("/{id}/image")
    public String showUploadForm(@PathVariable String id, Model model) {
        //model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
        model.addAttribute("id", id);
        return "recipe/imageuploadform";
    }

    @PostMapping("/{id}/image")
    public String handleImagePost(@PathVariable String id, @RequestParam("imagefile")MultipartFile file) {
        imageService.saveImageFile(Long.valueOf(id), file);

        return "redirect:/recipe/" + id + "/show";
    }

    @GetMapping("{id}/recipeimage")
    public void renderImageFromDb(@PathVariable String id, HttpServletResponse response) throws IOException {
        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(id));

        byte[] byteArray = recipeService.getImageByteArray(recipeCommand.getImage());
        response.setContentType("image/jpeg");
        if (byteArray != null) {
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        } else {

            //Below is an alternative way of loading a Resource from the class path
            //Here we used the @Value annotation with classpath:
            //InputStream is = resourceLoader.getResource("classpath:/static/images/guacamole400x400WithX.jpg").getInputStream();

            InputStream is = defaultImage.getInputStream();
            IOUtils.copy(is, response.getOutputStream());
        }
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNotFound(NotFoundException ex) {
        log.error("Handle not found Exception");
        ErrorCommand ec = ErrorCommand.builder().ex(ex).statusCode(HttpStatus.NOT_FOUND).build();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("error", ec);
        modelAndView.setViewName("ApplicationError");

        return modelAndView;

    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleNumberFormatException(NumberFormatException nfe) {
        log.error("Handling Number format exception");
        ErrorCommand ec = ErrorCommand.builder().ex(nfe).statusCode(HttpStatus.BAD_REQUEST).build();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("error", ec);
        modelAndView.setViewName("ApplicationError");

        return modelAndView;


    }
}
