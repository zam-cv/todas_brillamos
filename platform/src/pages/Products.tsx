import { Input } from "@/components/ui/input"
import { Button } from "@/components/ui/button"
import { columns } from "@/components/table/components/columns-products";
import { DataTable } from "@/components/table/components/data-table-products";
import { Textarea } from "@/components/ui/textarea"
import {useState} from 'react'
import { Product } from "../utils/api/products";

import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from "@/components/ui/accordion"


export default function Products() {
  const[products, setProducts] = useState([
    {
      idProducto: 'XDcaw1', 
      nombreProd: 'Producto #1', 
      cantidad: '12' , 
      precio: '12.00', 
      marca: 'Marca #1', 
      tamano: '12x12', 
      material: 'Material #1', 
      absorbencia: '12', 
      cuidadoPiel: 'Cuidado #1', 
      color: 'Color #1', 
      descripcion: 'Descripcion #1'
    }
  ]);

  const[formData, setFormData] = useState({
    nombreProd: '',
    cantidad: '',
    precio: '',
    marca: '',
    tamano: '',
    material: '',
    absorbencia: '',
    cuidadoPiel: '',
    color: '',
    descripcion: ''
  });

  const handleInputs = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  }
  
  const handleInputTextArea = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  }


  const handleAddProduct = () => {
    // Verificar si todos los campos están llenos
    if (Object.values(formData).every(field => field.trim() !== '')) {
      // Generar un ID único para el nuevo producto
      const newProduct = {
        idProducto: `ID${Math.random().toString(36).substr(2, 9)}`,
        ...formData,
      };
      setProducts([...products, newProduct]);

      // Limpiar el formulario
      setFormData({
        nombreProd: '',
        cantidad: '',
        precio: '',
        marca: '',
        tamano: '',
        material: '',
        absorbencia: '',
        cuidadoPiel: '',
        color: '',
        descripcion: ''
      });
    } else {
      alert('Por favor, completa todos los campos.');
    }
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
                    <Input name="nombreProd" value={formData.nombreProd} onChange={handleInputs} className="w-4/5" placeholder="Nombre del producto"></Input>
                    <Input name="cantidad" value={formData.cantidad} onChange={handleInputs} className="w-3/4 " placeholder="Cantidad"></Input>
                  </div>
                  <div className="flex flex-row space-x-2 px-2 pt-2">
                    <Input name="precio" value={formData.precio} onChange={handleInputs} placeholder="Precio"></Input>
                    <Input name="marca" value={formData.marca} onChange={handleInputs} placeholder="Marca"></Input>
                  </div>
                  <div className="flex flex-row space-x-2 px-2 pt-2">
                    <Input name= "tamano" value={formData.tamano} onChange={handleInputs} placeholder="Tamaño"></Input>
                    <Input name="material" value={formData.material} onChange={handleInputs} placeholder="Material"></Input>
                  </div>
                  <div className="flex flex-row space-x-2 px-2 pt-2">
                    <Input name= "absorbencia" value={formData.absorbencia} onChange={handleInputs} placeholder="Absorbencia"></Input>
                    <Input name="cuidadoPiel" value={formData.cuidadoPiel} onChange={handleInputs} placeholder="Cuidado de la piel"></Input>
                    <Input name= "color" value={formData.color} onChange= {handleInputs}placeholder="Color"></Input>
                  </div>
                  <div className="flex flex-row space-x-2 px-2 pt-2">
                      <Textarea name="descripcion" value={formData.descripcion} onChange={handleInputTextArea} placeholder="Descripción.."/>
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
      data = {products}
      columns = {columns}
      event_id = {1}
    />
      
    </div>
  );
        
  
  
}