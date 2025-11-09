package com.lab5.event_tracker.Controller;

import com.lab5.event_tracker.Api.ApiResponse;
import com.lab5.event_tracker.Model.EventTrackerSystem;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/event-tracker")
public class EventTrackerController {
    private ArrayList<EventTrackerSystem> events = new ArrayList<>();

    @GetMapping("/get-events")
    public ArrayList<EventTrackerSystem> getEvents(){
        return events;
    }

    @GetMapping("/search-event-id/{id}")
    public EventTrackerSystem getEventsById(@PathVariable String id){
        for (EventTrackerSystem event : events){
            if(event.getId().equalsIgnoreCase(id)){
                return event;
            }
        }
        return null;
    }

    @PostMapping("/create-event")
    public ApiResponse eventCreate(@RequestBody EventTrackerSystem event){
        if(event.getCapacity() <= 0 || event.getDescription() == null ||
                event.getEndDate() == null || event.getId() == null || event.getStartDate() == null ||
        event.getStartDate().isAfter(event.getEndDate())){
            return new ApiResponse("Please fill all the event information correctly", "404");
        }
        events.add(event);
        return new ApiResponse("event created successfully", "200");
    }


    @PutMapping("/update-event/{id}")
    public ApiResponse eventUpdate(@PathVariable String id, @RequestBody EventTrackerSystem NewEvent) {
        for (int i = 0; i< events.size(); i++){
            if(events.get(i).getId().equalsIgnoreCase(id)){
                if (NewEvent.getCapacity() == 0 || NewEvent.getDescription() == null ||
                        NewEvent.getEndDate() == null || NewEvent.getId() == null ||
                        NewEvent.getStartDate() == null||
                        NewEvent.getStartDate().isAfter(NewEvent.getEndDate())) {
                        return new ApiResponse("Please fill all the event information correctly", "404");
                } else {
                    events.set(i, NewEvent);
                    return new ApiResponse("event updated successfully", "200");

                }
            }
        }
        return new ApiResponse("event not found", "404");
    }

    @PutMapping("/update-capacity/{id}/{capacity}")
    public ApiResponse eventCapacityUpdate(@PathVariable String id, @PathVariable int capacity) {
        for (int i = 0; i< events.size(); i++){
            if(events.get(i).getId().equalsIgnoreCase(id)){
                if (capacity <= 0) {
                    return new ApiResponse("Please enter a valid capacity", "404");
                } else {
                    events.get(i).setCapacity(capacity);
                    return new ApiResponse("event capacity updated successfully", "200");
                }
            }
        }
        return new ApiResponse("event not found", "404");
    }

    @DeleteMapping("/delete-event/{id}")
    public ApiResponse eventDelete(@PathVariable String id){
        for (int i = 0; i< events.size(); i++){
            if(events.get(i).getId().equalsIgnoreCase(id)){
                events.remove(i);
                return new ApiResponse("event deleted successfully", "200");
                }
            }
        return new ApiResponse("event not found", "404");
    }


}
