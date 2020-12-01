 package models;

 import org.junit.After;
 import org.junit.Before;
 import org.junit.Test;

 import java.time.LocalDateTime;

 import static org.junit.Assert.*;

 public class PostTest {

     @Before
     public void setUp() throws Exception {
     }

     @After
     public void tearDown() throws Exception {
         Post.clearAllPosts();
     }

     @Test
     public void NewPostObjectGetsCorrectlyCreated_true() throws Exception {
         Post post = new Post("Day 1: Intro");
         assertTrue(post instanceof Post);
     }

     @Test
     public void PostInstantiatesWithContent_true() throws Exception {
         Post post = new Post("Day 1: Intro");
         assertEquals("Day 1: Intro", post.getContent());
     }

     @Test
     public void AllPostsAreCorrectlyReturned_true() {
         Post post = new Post("Day 1: Intro");
         Post otherPost = new Post("How to pair successfully");
         assertEquals(2, Post.getAll().size());
     }

     @Test
     public void AllPostsContainsAllPosts_true() {
         Post post = new Post("Day 1: Intro");
         Post otherPost = new Post("How to pair successfully");
         assertTrue(Post.getAll().contains(post));
         assertTrue(Post.getAll().contains(otherPost));
     }

     @Test
     public void getPublished_isFalseAfterInstantiation_false() {
         Post post = new Post("Day 1: Intro");
         assertEquals(false, post.getPublished());
     }

     @Test
     public void getCreatedAt_instantiatesWithCurrentTime_today() {
         Post post = setUpNewPost();
         assertEquals(LocalDateTime.now().getDayOfWeek(), post.getCreatedAt().getDayOfWeek());
     }

     @Test
     public void getId_postsInstantiateWithAnID_1() {
         Post post = setUpNewPost();
         assertEquals(1, post.getId());
     }

     @Test
     public void findReturnsCorrectPost() {
         Post post =  setUpNewPost();
         assertEquals(1, post.findById(post.getId()).getId());
     }

     @Test
     public void updateChangesPostContent() {
         Post post =  setUpNewPost();
         String formerContent =  post.getContent();
         LocalDateTime formerDate = post.getCreatedAt();
         int formerId = post.getId();

         post.updateThisPost("Android: Day 40");

         assertEquals(formerId, post.getId());
         assertEquals(formerDate, post.getCreatedAt());
         assertNotEquals(formerContent, post.getContent());
     }

     @Test
     public void deleteDeletesASpecificPost() {
         Post post = setUpNewPost();
         Post otherPost = new Post("How to pair successfully");
         post.deleteThisPost();
         assertEquals(1, Post.getAll().size());
         assertEquals(Post.getAll().get(0).getId(), 2);
     }

     @Test
     public void deleteAllPostsDeletesAllPosts() {
         Post post = setUpNewPost();
         Post otherPost = new Post("How to pair successfully");
         Post.clearAllPosts();
         assertEquals(0, Post.getAll().size());
     }




     public Post setUpNewPost() {
         return new Post("Day 1: Intro");
     }
 }