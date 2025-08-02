import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class WallServiceTest {

    @Test
    fun `update existing post should return true and update the text`() {
        // Arrange
        val service = WallService
        val originalPost = Post(1, "Старый текст", 0, 0)
        service.add(originalPost)

        val updatedPost = Post(1, "Новый текст", 100, 5)

        // Act
        val result = service.update(updatedPost)

        // Assert
        assertTrue(result, "Обновление существующего поста должно вернуть true")
        assertEquals("Новый текст", service.posts[0].text)
        assertEquals(100, service.posts[0].views)
        assertEquals(5, service.posts[0].likes)
        assertEquals(1, service.posts[0].id)
    }

    @Test
    fun `update non-existing post should return false`() {
        // Arrange
        val service = WallService
        val post = Post(1, "Тестовый пост", 0, 0)
        service.add(post)

        val nonExistingPost = Post(999, "Не существует", 0, 0)

        // Act
        val result = service.update(nonExistingPost)

        // Assert
        assertFalse(result, "Обновление несуществующего поста должно вернуть false")
        assertEquals("Тестовый пост", service.posts[0].text)
    }

    @Test
    fun `update should not modify original post object`() {
        // Arrange
        val service = WallService
        val originalPost = Post(1, "Исходный текст", 0, 0)
        service.add(originalPost)

        val updatedPost = Post(1, "Обновлённый текст", 50, 10)

        // Act
        service.update(updatedPost)

        // Assert
        assertEquals("Исходный текст", originalPost.text)
        assertEquals("Обновлённый текст", service.posts[0].text)
    }

    @Test
    fun `update with same data should still return true`() {
        // Arrange
        val service = WallService
        val post = Post(1, "Пост", 0, 0)
        service.add(post)

        val samePost = Post(1, "Пост", 0, 0)

        // Act
        val result = service.update(samePost)

        // Assert
        assertTrue(result, "Обновление с теми же данными должно вернуть true")
        assertEquals("Пост", service.posts[0].text)
    }

    @Test
    fun `add new post should increment lastId and return correct post`() {
        // Arrange
        val service = WallService
        val initialPostsCount = service.posts.size

        // Act
        val post = Post(0, "Тестовый пост", 0, 0)
        val addedPost = service.add(post)

        // Assert
        assertEquals(initialPostsCount + 1, service.posts.size)
        assertEquals(1, addedPost.id)
        assertEquals("Тестовый пост", addedPost.text)
        assertEquals(0, addedPost.views)
        assertEquals(addedPost, service.posts[0])
    }
}