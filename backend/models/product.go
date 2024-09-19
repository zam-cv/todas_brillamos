package models

type Product struct {
	ID          uint    `json:"-" gorm:"primarykey"`
	Name        string  `json:"name"`
	Description string  `json:"description"`
	Price       float64 `json:"price"`
	Stock       int     `json:"stock"`
	Hash        string  `json:"hash"`
	Type        string  `json:"type"`
	CategoryID  uint    `json:"category_id"`
}

type ProductMetadata struct {
	Name        string  `json:"name" validate:"required,min=1"`
	Description string  `json:"description" validate:"required"`
	Price       float64 `json:"price" validate:"required,min=0"`
	Stock       int     `json:"stock" validate:"required,min=0"`
	CategoryID  uint    `json:"category_id" validate:"required,min=0"`
}

func (p *Product) SetID(id uint) {
	p.ID = id
}

func (p *Product) GetHash() *string {
	return &p.Hash
}

func (p *Product) GetType() *string {
	return &p.Type
}

func (p *Product) SetMetadata(metadata *ProductMetadata) {
	if metadata == nil {
		return
	}

	p.Name = metadata.Name
	p.Description = metadata.Description
	p.Price = metadata.Price
	p.Stock = metadata.Stock
	p.CategoryID = metadata.CategoryID
}
