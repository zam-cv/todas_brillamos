package models

type Product struct {
	ID uint `json:"-" gorm:"primarykey"`

	// Image
	Hash string `json:"hash"`
	Type string `json:"type"`

	// General
	Model       string  `json:"model"`
	Name        string  `json:"name"`
	Description string  `json:"description"`
	Price       float64 `json:"price"`
	Stock       int     `json:"stock"`

	// Specific
	Size        string `json:"size"`
	Color       string `json:"color"`
	Maintenance string `json:"maintenance"`
	Material    string `json:"material"`
	Absorbency  string `json:"absorbency"`
	SkinCare    string `json:"skin_care"`

	// Relationships
	CategoryID uint `json:"category_id"`
}

type ProductMetadata struct {
	// General
	Model       string  `json:"model" validate:"required,min=1"`
	Name        string  `json:"name" validate:"required,min=1"`
	Description string  `json:"description" validate:"required"`
	Price       float64 `json:"price" validate:"required,min=0"`
	Stock       int     `json:"stock" validate:"required,min=0"`

	// Specific
	Size        string  `json:"size" validate:"required"`
	Color       string  `json:"color" validate:"required"`
	Maintenance string  `json:"maintenance" validate:"required"`
	Material    string  `json:"material" validate:"required"`
	Absorbency  string  `json:"absorbency" validate:"required"`
	SkinCare    string  `json:"skin_care" validate:"required"`

	// Relationships
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

	p.Model = metadata.Model
	p.Name = metadata.Name
	p.Description = metadata.Description
	p.Price = metadata.Price
	p.Stock = metadata.Stock
	p.Size = metadata.Size
	p.Color = metadata.Color
	p.Maintenance = metadata.Maintenance
	p.Material = metadata.Material
	p.Absorbency = metadata.Absorbency
	p.SkinCare = metadata.SkinCare
	p.CategoryID = metadata.CategoryID	
}
