package org.javers.core.cases;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.javers.core.*;
import org.javers.core.metamodel.annotation.Id;
import org.javers.repository.jql.*;
import org.javers.shadow.Shadow;
import org.junit.jupiter.api.*;

public class GetLatestVersionTest {
	private static final String FIRST_STRING = "This string should be in the end result";
	private static final String SECOND_STRING = "This string will get overwritten with a newer version";
	private static final String THIRD_STRING = "This string should also be in the end result";
	private static final String FOURTH_STRING = "This string should be in the end result as well";
	private static final String AUTHOR_NAME = "Test Author";

	@Test
	public void shouldRetrieveOnlyLatestVersionsOfAllObjects() {
		Javers javers = JaversBuilder.javers().build();
		StringContainer firstObject = new StringContainer(FIRST_STRING);
		javers.commit(AUTHOR_NAME, firstObject);
		StringContainer secondObject = new StringContainer(SECOND_STRING);
		javers.commit(AUTHOR_NAME, secondObject);
		StringContainer thirdObject = new StringContainer(THIRD_STRING);
		javers.commit(AUTHOR_NAME, thirdObject);
		secondObject.string = FOURTH_STRING;
		javers.commit(AUTHOR_NAME, secondObject);
		
		//At this point, some logic is needed that will only query the latest version
		JqlQuery entryQuery = QueryBuilder.byClass(StringContainer.class).withVersion(-1).build();
		List<Shadow<StringContainer>> mostRecentShadows = javers.findShadows(entryQuery);
		
		assertEquals(3, mostRecentShadows.size());
		List<String> mostRecentStrings = new ArrayList<>();
		for(Shadow<StringContainer> shadow : mostRecentShadows) {
			mostRecentStrings.add(shadow.get().string);
		}
		assertTrue(mostRecentStrings.contains(FIRST_STRING));
		assertFalse(mostRecentStrings.contains(SECOND_STRING));
		assertTrue(mostRecentStrings.contains(THIRD_STRING));
		assertTrue(mostRecentStrings.contains(FOURTH_STRING));
	}
}

class StringContainer {
	@Id
	public String string;
	
	public StringContainer(String string) {
		this.string = string;
	}
}