package database

import "backend/models"

func GetPostByID(id uint) (*models.Post, error) {
	post := &models.Post{}
	if err := db.First(post, id).Error; err != nil {
		return nil, err
	}

	return post, nil
}

func GetPosts() ([]models.Post, error) {
	var posts []models.Post
	if err := db.Find(&posts).Error; err != nil {
		return nil, err
	}

	return posts, nil
}

func CreatePost(post *models.Post) (uint, error) {
	if err := db.Create(post).Error; err != nil {
		return 0, err
	}

	return post.ID, nil
}

func DeletePost(id uint) (*models.Post, error) {
	post, err := GetPostByID(id)
	if err != nil {
		return nil, err
	}

	if err := db.Delete(post).Error; err != nil {
		return nil, err
	}

	return post, nil
}