package com.lab5.project_tracker.Controller;

import com.lab5.project_tracker.Api.ApiResponse;
import com.lab5.project_tracker.Model.ProjectTrackerSystem;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
// search by title, display all for one company name
@RestController
@RequestMapping("/api/v1/project-tracker")
public class ProjectTrackerController {
    private ArrayList<ProjectTrackerSystem> projects = new ArrayList<>();

    @GetMapping("/get-projects")
    public ArrayList<ProjectTrackerSystem> getProjects(){
        return projects;
    }

    @GetMapping("/search-project-title/{title}")
    public ProjectTrackerSystem getProjectsByTitle(@PathVariable String title){
        for(ProjectTrackerSystem project : projects){
            if(project.getTitle().equalsIgnoreCase(title)){
                return project;
            }
        }
        return null;
    }

    @GetMapping("/search-project-company-name/{companyName}")
    public ArrayList<ProjectTrackerSystem> getProjectsByCompanyName(@PathVariable String companyName){
        ArrayList<ProjectTrackerSystem> companyProjects = new ArrayList<>();
        for(ProjectTrackerSystem project : projects){
            if(project.getCompanyName().equalsIgnoreCase(companyName)){
                companyProjects.add(project);
            }
        }
        return companyProjects;
    }


    @PostMapping("/create-project")
    public ApiResponse projectCreate(@RequestBody ProjectTrackerSystem project){
        if(project.getCompanyName() == null || project.getDescription() == null
                || project.getTitle() == null || project.getId() == null){
            return new ApiResponse("Please fill all the project information", "404");
        }
        projects.add(project);
        return new ApiResponse("project added successfully", "200");
    }

    @PutMapping("/update-project/{id}")
    public ApiResponse projectUpdate(@PathVariable String id, @RequestBody ProjectTrackerSystem project){
        for (int i = 0; i< projects.size(); i++){
            if(projects.get(i).getId().equalsIgnoreCase(id)){
            if(project.getCompanyName() == null || project.getDescription() == null
                    || project.getTitle() == null || project.getId() == null){
                return new ApiResponse("Please fill all the project information", "404");
            }else {
                projects.set(i, project);
                return new ApiResponse("project updated successfully", "200");
            }
            }
        }
        return new ApiResponse("project not found", "404");
    }

    @PutMapping("/update-status/{id}")
    public ApiResponse projectStatusUpdate(@PathVariable String id){
        for (int i = 0; i< projects.size(); i++){
            if(projects.get(i).getId().equalsIgnoreCase(id)){
            if(projects.get(i).isDone()){
                return new ApiResponse("project status is already done", "200");
            }else {
                projects.get(i).setDone(true);
                return new ApiResponse("project status updated successfully", "200");
            }
            }
        }
        return new ApiResponse("project not found", "404");
    }

    @DeleteMapping("/delete-project/{id}")
    public ApiResponse projectDelete(@PathVariable String id){
        for (int i = 0; i< projects.size(); i++){
            if(projects.get(i).getId().equalsIgnoreCase(id)){
                projects.remove(i);
                return new ApiResponse("project deleted successfully", "200");
            }
        }
        return new ApiResponse("project not found", "404");
    }

}
