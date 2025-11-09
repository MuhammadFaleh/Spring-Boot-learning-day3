package com.lab5.studentsGpa.Controller;

import com.lab5.studentsGpa.Api.ApiResponse;
import com.lab5.studentsGpa.Model.StudentSystem;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/student")
public class StudentsController {
    private ArrayList<StudentSystem> students = new ArrayList<>();

    @GetMapping("/get-students")
    public ArrayList<StudentSystem> getStudents(){
        return students;
    }

    @GetMapping("/get-above-avg")
    public ArrayList<StudentSystem> getStudentAboveAvg(){
        double avg = 0;
        ArrayList<StudentSystem> aboveAvgStudents = new ArrayList<>();
        for(StudentSystem student : students){
            avg += student.getGpa();
        }
        avg /= students.size();

        for (StudentSystem student : students){
            if(student.getGpa() > avg){
                aboveAvgStudents.add(student);
            }
        }
        return aboveAvgStudents;
    }

    // first 4.75 to 5.0 and second 4.25 to 4.74
    @GetMapping("/get-honors")
    public ArrayList<ArrayList<StudentSystem>> getStudentsHonorCategory(){
        ArrayList<StudentSystem> firstHonor = new ArrayList<>();
        ArrayList<StudentSystem> secondHonor = new ArrayList<>();

        for(StudentSystem student : students){
            if(student.getGpa() >= 4.75){
                student.setHonorsCategory("First Honors");
                firstHonor.add(student);
            }else if(student.getGpa() < 4.75 && student.getGpa() >= 4.25){
                student.setHonorsCategory("Second Honors");
                secondHonor.add(student);
            }else student.setHonorsCategory("No Honors status");
        }
        ArrayList<ArrayList<StudentSystem>> finalList = new ArrayList<>();
        finalList.add(firstHonor);
        finalList.add(secondHonor);
        return finalList;
    }

    @PostMapping("/create-student")
    public ApiResponse studentCreate(@RequestBody StudentSystem student){
        if(student.getAge() <= 0 || student.getGpa() <= 0 || student.getDegree() == null
                || student.getId() == null || student.getName() == null){
            return new ApiResponse("Please fill all student the information", "404");
        }
        if(student.getGpa() >= 4.75){
            student.setHonorsCategory("First Honors");

        }else if(student.getGpa() < 4.75 && student.getGpa() >= 4.25){
            student.setHonorsCategory("Second Honors");
        } else student.setHonorsCategory("No Honors status");

        students.add(student);

        return new ApiResponse("student created successfully", "200");
    }

    @PutMapping("/update-student/{id}")
    public ApiResponse studentUpdate(@PathVariable String id, @RequestBody StudentSystem student){
        for (int i = 0; i< students.size(); i++){
            if(students.get(i).getId().equalsIgnoreCase(id)){
                if(student.getAge() <= 0 || student.getGpa() <= 0 || student.getDegree() == null
                || student.getId() == null || student.getName() == null){
                return new ApiResponse("Please fill all student the information", "404");
                    } else {
                        if(student.getGpa() >= 4.75){
                            student.setHonorsCategory("First Honors");
                        }else if(student.getGpa() < 4.75 && student.getGpa() >= 4.25){
                            student.setHonorsCategory("Second Honors");
                        } else student.setHonorsCategory("No Honors status");
                        students.set(i, student);
                        return new ApiResponse("Student updated successfully", "200");
                }
            }
        }
        return new ApiResponse("user not found", "404");
    }



    @DeleteMapping("/delete-student/{id}")
    public ApiResponse studentDelete(@PathVariable String id){
        for (int i = 0; i< students.size(); i++){
            if(students.get(i).getId().equalsIgnoreCase(id)){
                students.remove(i);
                return new ApiResponse("student deleted successfully", "200");
            }
        }
        return new ApiResponse("user not found", "404");
    }


}
