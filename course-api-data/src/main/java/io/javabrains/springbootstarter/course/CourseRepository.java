package io.javabrains.springbootstarter.course;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * 
 * @author dante
 *
 */
public interface CourseRepository extends CrudRepository<Course, String> {
	
	//getAllTopics()
	//getTopic(String id)
	//updateTopic(Topic t)
	//deleteTopic(String id)
	
	public List<Course> findByTopicId(String topicId);
	
	public List<Course> findByName(String name);
	
	public List<Course> findByDescription(String description);

}
