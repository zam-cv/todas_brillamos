import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { columns } from "@/components/table/components/columns-products";
import { DataTable } from "@/components/table/components/data-table-products";
import { Textarea } from "@/components/ui/textarea";
import { useEffect, useState, useRef } from "react";
import api from "@/utils/api/products";

import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from "@/components/ui/accordion";



  // {
  //   "model": "0017",
  //   "name": "prod23",
  //   "description": "descripcion",
  //   "price": 20,
  //   "stock": 4,
  //   "size": "21 x 7 cm",
  //   "color": "negro",
  //   "maintenance": "Lavable y reutilizable",
  //   "material": "Algodón",
  //   "absorbency": "Alta",
  //   "material_feature": "Hipoalergénica, transpirable",
  //   "category_id": 1
  // }

export default function UploadProducts() {
  const imageInput = useRef<HTMLInputElement>(null);
  const [model, setModel] = useState<string>("");
  const [name, setName] = useState<string>("");
  const [description, setDescription] = useState<string>("");
  const [price, setPrice] = useState<number>(0);
  const [stock, setStock] = useState<number>(0);
  const [size, setSize] = useState<string>("");
  const [color, setColor] = useState<string>("");
  const [maintenance, setMaintenance] = useState<string>("");
  const [material, setMaterial] = useState<string>("");
  const [absorbency, setAbsorbency] = useState<string>("");
  const [material_feature, setMaterial_feature] = useState<string>("");
  const [category_id, setCategory_id] = useState<number>(0);

  function uploadProduct() {
    api.product.setProduct(imageInput as any, {
      model,
      name,
      description,
      price,
      stock,
      size,
      color,
      maintenance,
      material,
      absorbency,
      material_feature,
      category_id,
    }).then(() => {
      alert("Producto agregado");
    })
  }
  return (
    <div>
      <div className="flex justify-between items-center">
        <h2 className="scroll-m-20 text-3xl font-semibold tracking-tight first:mt-0">
          Productos
        </h2>
        {/* <Button
          onClick={fetchProducts}
          disabled={loading}
          variant="outline"
          className="flex items-center gap-2"
        >
          <RefreshCw className={`h-4 w-4 ${loading ? "animate-spin" : ""}`} />
          Actualizar
        </Button> */}
      </div>
      <br />
      <Accordion type="single" collapsible className="w-3/4">
        <AccordionItem value="item-1">
          <AccordionTrigger className="font-semibold">Agregar</AccordionTrigger>
          <AccordionContent>
            <div className="flex flex-nowrap flex-col">
              <div className="flex flex-row space-x-2 px-2 pt-2">
                <Input
                  name="name"
                  value={name}
                  onChange={(e) => setName(e.target.value)}
                  className="w-4/5"
                  placeholder="Nombre del producto"
                  required
                />
                <Input
                  name="stock"
                  value={stock}
                  onChange={(e) => setStock(Number(e.target.value))}
                  className="w-3/4"
                  placeholder="Cantidad"
                  type="number"
                  required
                />
              </div>
              <div className="flex flex-row space-x-2 px-2 pt-2">
                <Input
                  name="price"
                  value={price}
                  onChange={(e) => setPrice(Number(e.target.value))}
                  placeholder="Precio"
                  type="number"
                  required
                />
                <Input
                  name="model"
                  value={model}
                  onChange={(e) => setModel(e.target.value)}
                  placeholder="Modelo"
                  required
                />
              </div>
              <div className="flex flex-row space-x-2 px-2 pt-2">
                <Input
                  name="size"
                  value={size}
                  onChange={(e) => setSize(e.target.value)}
                  placeholder="Tamaño"
                />
                <Input
                  name="material"
                  value={material}
                  onChange={(e) => setMaterial(e.target.value)}
                  placeholder="Material"
                />
              </div>
              <div className="flex flex-row space-x-2 px-2 pt-2">
                <Input
                  name="absorbency"
                  value={absorbency}
                  onChange={(e) => setAbsorbency(e.target.value)}
                  placeholder="Absorbencia"
                />
                <Input
                  name="material_feature"
                  value={material_feature}
                  onChange={(e) => setMaterial_feature(e.target.value)}
                  placeholder="Cuidado de la piel"
                />
                <Input
                  name="color"
                  value={color}
                  onChange={(e) => setColor(e.target.value)}
                  placeholder="Color"
                />
                <Input
                  name="maintenance"
                  value={maintenance}
                  onChange={(e) => setMaintenance(e.target.value)}
                  placeholder="Manteinance"
                />
              </div>
              <div className="flex flex-row space-x-2 px-2 pt-2">
                <Textarea
                  name="description"
                  value={description}
                  onChange={(e) => setDescription(e.target.value)}
                  placeholder="Descripción.."
                />
              </div>
              <div className="flex flex-row space-x-2 px-2 pt-2">
                <Input
                  name="category_id"
                  value={category_id}
                  onChange={(e) => setCategory_id(Number(e.target.value))}
                  placeholder="ID de categoría"
                  type="number"
                  required
                />
              </div>
              <div className="flex flex-row space-x-2 px-2 pt-2">
                <Input ref={imageInput} type="file" onChange={() => {}} required />
              </div>
              <div className="flex flex-row space-x-2 px-2 pt-2">
                <Button onClick={uploadProduct}>
                </Button>
              </div>
            </div>
          </AccordionContent>
        </AccordionItem>
      </Accordion>

      <br />

      {/* <DataTable data={} columns={columns} event_id={1} /> */}
    </div>
  );
}

