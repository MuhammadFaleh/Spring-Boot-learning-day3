package com.lab5.project_tracker.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectTrackerSystem {
   private String id;
   private String title;
   private String description;
   private boolean isDone;
   private String companyName;
}
