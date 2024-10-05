/*
 * Backend-models: Código que contiene el modelo de Post y sus atributos
 * @author: Jennnyfer Jasso
 */

package models

/*
 * Estructura de la tabla Post
 */
type Post struct {
	ID      uint   `json:"id" gorm:"primarykey" validate:"omitempty,eq=0"`
	Title   string `json:"title" validate:"required,min=1"`
	Author  string `json:"author" validate:"required,min=1"`
	Date    string `json:"date" validate:"required,min=1"`
	Content string `json:"content" validate:"required,min=1"`
}
