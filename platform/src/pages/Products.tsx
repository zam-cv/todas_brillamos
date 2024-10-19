import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { createColumns } from "@/components/table/components/columns-products";
import { DataTable } from "@/components/table/components/data-table-products";
import { useEffect, useState, useRef } from "react";
import api, { Product } from "@/utils/api/products";
import apiCategory, { Category } from "@/utils/api/category";
import { useNavigate } from "react-router-dom";
import { SERVER } from "@/utils/constants";
import DynamicInputFields from "@/components/DynamicTextField";


import {
  Accordion,
  AccordionContent,
  AccordionItem,
  AccordionTrigger,
} from "@/components/ui/accordion";

import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { SelectGroup, SelectLabel } from "@radix-ui/react-select";

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

/**
 * Página de productos
 * @author Sebastian Antonio Almanza
 */

export default function UploadProducts() {
  // Constantes para almacenar los datos del producto
  const imageInput = useRef<HTMLInputElement>(null);
  const [model, setModel] = useState<string>("");
  const [name, setName] = useState<string>("");

  const [description, setDescription] = useState<string>("");


  const [price, setPrice] = useState<number>(20);
  const [stock, setStock] = useState<number>(0);
  const [size, setSize] = useState<string>("");
  const [color, setColor] = useState<string>("");
  const [maintenance, setMaintenance] = useState<string>("");
  const [material, setMaterial] = useState<string>("");
  const [absorbency, setAbsorbency] = useState<string>("");
  const [material_feature, setMaterial_feature] = useState<string>("");
  const [category_id, setCategory_id] = useState<number>(0);
  const [products, setProducts] = useState<Product[]>([]);
  const [id, setId] = useState<number | null>(null);
  const [isFormValid, setIsFormValid] = useState(false);
  const navigate = useNavigate();
  const [folder, setFolder] = useState<string>("");


  //Revisar que el fomulario este completo
  useEffect(() => {
    const formValid =
      model !== "" &&
      name !== "" &&
      description !== "" &&
      price > 0 &&
      stock > 0 &&
      category_id > 0 &&
      size !== "" &&
      color !== "" &&
      maintenance !== "" &&
      material !== "" &&
      absorbency !== "" &&
      material_feature !== "";
    setIsFormValid(formValid);
  }, [
    model,
    name,
    description,
    price,
    stock,
    color,
    maintenance,
    material,
    absorbency,
    material_feature,
    category_id,
  ]);

  //Función para subir el producto a la base de datos
  function uploadProduct() {
    if (imageInput.current) {
      const file = imageInput.current.files?.[0];
      if (!file) {
        return;
      }

      api.product
        .setProduct(file, {
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
        } as any)
        .then((data) => {
          const id = data.id;

          setProducts([
            ...products,
            {
              id,
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
              url: `${SERVER}/${folder}/${data.name}`,
            } as any,
          ]);

          // alert("Producto agregado");
          setModel("");
          setName("");
          setDescription("");
          setPrice(0);
          setStock(0);
          setSize("");
          setColor("");
          setMaintenance("");
          setMaterial("");
          setAbsorbency("");
          setMaterial_feature("");
          setCategory_id(0);
          if (imageInput.current) {
            imageInput.current.value = "";
          }
        });
    }
  }

  //Función para actualizar la información de los productos
  function updateProduct(value: Object, fields: Product) {
    api.product.updateMetadataProduct(fields.id, {
      ...fields,
      ...value,
    } as any);
  }

  //Constante para almacenar las categorías de la base de datos
  const [categories, setCategories] = useState<Category[]>([]);

  //Obtener las categorías de la base de datos
  useEffect(() => {
    apiCategory.category.getCategories().then((categories) => {
      console.log(categories);
      setCategories(categories);
    });
  }, []);


  //Obtener los productos de la base de datos
  useEffect(() => {
    api.product.getProducts().then(([products, folder]) => {
      setProducts(products);
      setFolder(folder);
    });
  }, []);

  //Obtener el id del producto
  useEffect(() => {
    console.log(id);
  }, [id]);

  //Revisar si el token esta en el local storage
  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) {
      window.location.href = "/login";
    }
  }, [navigate]);

  // function handleDelete(productId: number) {
  //   api.product
  //     .deleteProduct(productId)
  //     .then(() => {
  //       setProducts(products.filter((product) => product.id !== productId));
  //       console.log("Producto eliminado");
  //     })
  //     .catch((error) => {
  //       console.error("Error al eliminar el producto:", error);
  //     });
  // }

  //Crear las columnas de la tabla
  const columns = createColumns(updateProduct);
  return (
    <div className="space-y-4 relative w-full h-full">
      <div className="w-full absolute">
        <div className="flex justify-between items-center">
          <h2 className="scroll-m-20 text-3xl font-semibold tracking-tight first:mt-0">
            Productos
          </h2>
        </div>
        <br />
        <Accordion type="single" collapsible className="w-3/4">
          <AccordionItem value="item-1">
            <AccordionTrigger className="font-semibold">
              Agregar
            </AccordionTrigger>
            <AccordionContent>
              <div className="flex flex-wrap flex-col">
                <div className="flex flex-rows space-x-2 px-2 pt-2">
                  <label className="w-3/4">
                    <span className="font-semibold">Nombre</span>
                    <Input
                      name="name"
                      value={name}
                      onChange={(e) => setName(e.target.value)}
                      placeholder="Toalla reusable...."
                      required
                    />
                  </label>
                  <label className="w-3/4">
                    <span className="font-semibold">Stock</span>
                    <Input
                      name="stock"
                      value={stock}
                      onChange={(e) => {
                        const inputValue = Number(e.target.value);
                        const maxInt32 = 2147483647;

                        if(inputValue <= maxInt32){
                          setStock(inputValue);
                        } else {
                          alert(`El stock no puede ser mayor a ${maxInt32}`)
                        }
                      }}
                      className="w-1/3"
                      placeholder="Cantidad"
                      type="number"
                    />
                  </label>
                </div>
                <div className="flex flex-row space-x-2 px-2 pt-2">
                    <label className="w-1/5">
                    <span className="font-semibold">Precio</span>
                    <Input
                      name="price"
                      value={price}
                      onChange={(e) => {
                      const newValue = Math.max(20, Number(e.target.value));
                      setPrice(newValue);
                      }}
                      placeholder="20"
                      type="number"
                      min="20"
                      required
                      
                    />
                    </label>
                  <label className="w-3/5">
                    <span className="font-semibold">Modelo</span>
                    <Input
                      name="model"
                      value={model}
                      onChange={(e) => setModel(e.target.value)}
                      placeholder="GX-1201.."
                      required
                    />
                  </label>
                  <label className="w-1/5">
                    <span className="font-semibold">Tamaño</span>
                    <Input
                      name="size"
                      value={size}
                      onChange={(e) => setSize(e.target.value)}
                      placeholder="12x12.."
                    />
                  </label>
                </div>
                <div className="flex flex-row space-x-2 px-2 pt-2">
                  <label className="w-2/4">
                    <span className="font-semibold">Material</span>
                    <Input
                      name="material"
                      value={material}
                      onChange={(e) => setMaterial(e.target.value)}
                      placeholder="fibras de algodón.."
                    />
                  </label>
                  <label className="w-2/4">
                    <span className="font-semibold">Absorbencia</span>
                    <Input
                      name="absorbency"
                      value={absorbency}
                      onChange={(e) => setAbsorbency(e.target.value)}
                      placeholder="Alta.."
                    />
                  </label>
                </div>
                <div className="flex flex-row space-x-2 px-2 pt-2">
                  <label className="w-3/4">
                    <span className="font-semibold">
                      Características del material
                    </span>
                    <Input
                      name="material_feature"
                      value={material_feature}
                      onChange={(e) => setMaterial_feature(e.target.value)}
                      placeholder="Hipoalergénica..."
                    />
                  </label>
                  <label className="w-1/4">
                    <span className="font-semibold">Color</span>
                    <Input
                      name="color"
                      value={color}
                      onChange={(e) => setColor(e.target.value)}
                      placeholder="Azul.."
                    />
                  </label>
                </div>
                <div className="grid space-x-2 px-2 pt-2">
                  <label>
                    <span className="font-semibold">Mantenimiento</span>
                    <Input
                      name="maintenance"
                      value={maintenance}
                      onChange={(e) => setMaintenance(e.target.value)}
                      placeholder="Lavable..."
                    />
                  </label>
                </div>
                <div className="grid space-x-2 px-2 pt-2">
                  <label>
                    <span className="font-semibold">Descripción</span>
                    <div className="grid space-x-2 px-2 pt-2">
                      <label>
                       
                        <DynamicInputFields 
                          description={description}
                          setDescription={setDescription}
                        />
                      </label>
                    </div>
                  </label>
                </div>
                <div className="flex flex-row space-x-2 px-2 pt-2">
                  <label className="w-2/4">
                    <span className="font-semibold">Categoría</span>
                    <Select
                      value={category_id.toString()}
                      onValueChange={(value) => setCategory_id(Number(value))}
                    >
                      <SelectTrigger>
                        <SelectValue placeholder="Selecciona una categoría" />
                      </SelectTrigger>
                      <SelectContent>
                        <SelectGroup>
                          <SelectLabel>Categorías</SelectLabel>
                          {categories.map((category) => (
                            <SelectItem
                              key={category.id}
                              value={category.id.toString()}
                            >
                              {category.name}
                            </SelectItem>
                          ))}
                        </SelectGroup>
                      </SelectContent>
                    </Select>
                  </label>
                </div>
                <div className="flex flex-row space-x-2 px-2 pt-2">
                  <label>
                    <span className="font-semibold">Imagen</span>
                    <Input
                      ref={imageInput}
                      type="file"
                      onChange={() => {}}
                      required
                    />
                  </label>
                </div>
                <div className="flex flex-row space-x-2 px-2 pt-2">
                  <Button onClick={uploadProduct} disabled={!isFormValid}>
                    Agregar Producto
                  </Button>
                </div>
              </div>
            </AccordionContent>
          </AccordionItem>
        </Accordion>
        <br />

        <DataTable data={products} columns={columns} setId={setId} />
      </div>
    </div>
  );
}
