package 
io.javabrains.springbootstarter.course;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author dante
 *
 */
@Service
public class CourseService {
	
	@Autowired
	private CourseRepository coursecRepository;

//	private List<Topic> topics = new ArrayList<>(Arrays.asList(
//			new Topic("spring", "Spring Framework", "Spring Framework Description"),
//			new Topic("java", "Core Java", "Core Java Description"),
//			new Topic("javascript", "Javascript", "Javascript Description")
//			));
	
	public List<Course> getAllCourses(String topicId) {
//		return topics;
		List<Course> courses = new ArrayList<>();
		coursecRepository.findByTopicId(topicId).forEach(courses::add);
		return courses;
	}
	
	public Course getCourse(String id) {
//		return topics.stream().filter(t -> id.equals(t.getId())).findFirst().get();
		return coursecRepository.findOne(id);
	}

	public void addCourse(Course course) {
		//topics.add(topic);
		coursecRepository.save(course);
	}

//	public void updateCourse(String id, Course course) {
	public void updateCourse(Course course) {
//		for (int i = 0; i < topics.size(); i++) {
//			Topic t = topics.get(i);
//			if (t.getId().equals(id)) {
//				topics.set(i, topic);
//				return;
//			}
//		}
		coursecRepository.save(course);
	}

	public void deleteCourse(String id) {
//		topics.removeIf(t -> t.getId().equals(id));
		coursecRepository.delete(id);
	}
}
