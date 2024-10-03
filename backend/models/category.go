package models

type Category struct {
	ID   uint   `json:"id" gorm:"primarykey" validate:"omitempty,eq=0"`
	Name string `json:"name"`
}
