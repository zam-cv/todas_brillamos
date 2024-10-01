import { Input } from "@/components/ui/input"
import { Button } from "@/components/ui/button"
import { columns } from "@/components/table/components/columns-products";
import { DataTable } from "@/components/table/components/data-table-products";
import { Textarea } from "@/components/ui/textarea"
import {useEffect, useState} from 'react'
import productAPI, {Product as ISProducts} from "@/utils/api/products";

import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from "@/components/ui/accordion"



export default function Products() {
  const[products, setProducts] = useState<ISProducts[]>([]);
  const[formData, setFormData] = useState({
    model: '',
    name: '',
    description: '',
    price: '',
    stock: '',
    size: '',
    color: '',
    manteinance: '',
    material: '',
    absorbency: '',
    material_feature: '',
    category_id: ''
  });

  
  useEffect(() => {
    productAPI.product
    .getProducts()
    .then((data: any) => {
        setProducts(data);
    })
    .catch((error) => console.error("Error al obtener productos:", error))
  }, ([]));



  const handleInputs = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  }
  
  const handleInputTextArea = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  }


  const handleAddProduct = () => {
      // Generar un ID único para el nuevo producto
      const newProduct: ISProducts = {
        model: formData.model,
        name: formData.name,
        description: formData.description,
        price: parseFloat(formData.price),
        stock: parseInt(formData.stock),
        size: formData.size,
        color: formData.color,
        manteinance: formData.manteinance,
        material: formData.material,
        absorbency: formData.absorbency,
        material_feature: formData.material_feature,
        category_id: parseInt(formData.category_id),
      };

      productAPI.product
        .setProduct(new File([], ""), newProduct)
        .then(() => {
          setProducts([...products, newProduct]);
           // Limpiar el formulario
          setFormData({
            model: '',
            name: '',
            description: '',
            price: '',
            stock: '',
            size: '',
            color: '',
            manteinance: '',
            material: '',
            absorbency: '',
            material_feature: '',
            category_id: ''
          });
        })
        .catch((error) => console.error("Error al agregar producto:", error));
    
  };



return(
    <div>
      <h2 className="scroll-m-20  text-3xl font-semibold tracking-tight first:mt-0">
          Productos
      </h2>
    <br></br>
    <Accordion type="single" collapsible className="w-3/4">
        <AccordionItem value="item-1">
          <AccordionTrigger className="font-semibold">Agregar</AccordionTrigger>
            <AccordionContent>
              <div className = "flex flex-nowrap flex-col">
                  <div className="flex flex-row space-x-2 px-2 pt-2">
                    <Input name="name" value={formData.name} onChange={handleInputs} className="w-4/5" placeholder="Nombre del producto"></Input>
                    <Input name="stock" value={formData.stock} onChange={handleInputs} className="w-3/4 " placeholder="Cantidad"></Input>
                  </div>
                  <div className="flex flex-row space-x-2 px-2 pt-2">
                    <Input name="price" value={formData.price} onChange={handleInputs} placeholder="Precio"></Input>
                    <Input name="model" value={formData.model} onChange={handleInputs} placeholder="Modelo"></Input>
                  </div>
                  <div className="flex flex-row space-x-2 px-2 pt-2">
                    <Input name= "size" value={formData.size} onChange={handleInputs} placeholder="Tamaño"></Input>
                    <Input name="material" value={formData.material} onChange={handleInputs} placeholder="Material"></Input>
                  </div>
                  <div className="flex flex-row space-x-2 px-2 pt-2">
                    <Input name= "absorbency" value={formData.absorbency} onChange={handleInputs} placeholder="Absorbencia"></Input>
                    <Input name="material_feature" value={formData.material_feature} onChange={handleInputs} placeholder="Cuidado de la piel"></Input>
                    <Input name= "color" value={formData.color} onChange= {handleInputs}placeholder="Color"></Input>
                  </div>
                  <div className="flex flex-row space-x-2 px-2 pt-2">
                      <Textarea name="description" value={formData.description} onChange={handleInputTextArea} placeholder="Descripción.."/>
                  </div>
                  <div className="flex flex-row space-x-2 px-2 pt-2">
                      <Button onClick={handleAddProduct}>Agregar</Button>
                  </div>
              </div>
            </AccordionContent>
        </AccordionItem>
    </Accordion>
    


    <br></br>

  
    <DataTable
      data={products}
      columns = {columns}
      event_id = {1}
    />
      
    </div>
  );
        
  
  
}