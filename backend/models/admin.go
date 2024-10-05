/*
 * Backend-models: Código que contiene el modelo de Administrador y sus atributos
 * @author: Carlos Zamudio
 */
package models

/*
 * Estructura de la tabla de Admin
 */
type Admin struct {
	ID     uint `json:"-" gorm:"primarykey"`
	UserID uint
}
