import { ColumnDef } from "@tanstack/react-table";
import { Task } from "../data/schema_products";
import { DataTableColumnHeader } from "./data-table-column-header-users";
import { Button } from "@/components/ui/button";
import {
  AlertDialog,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from "@/components/ui/alert-dialog"
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog"
import { useState } from "react";
import { Product } from "@/utils/api/products";


export const createColumns = (
  handleDelete: (id: number) => void,
  updateProduct: (value: Object, fields: Product) => void): ColumnDef<Task>[] => [
    {
      accessorKey: "name",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Nombre" />
      ),
      cell: ({ row }) => {
        const [name, setName] = useState<string>(row.getValue("name"));

        function handleSave(value: string) {
          updateProduct({ name: value }, (row.original as any));
          setName(value);
        }

        return (
          <div className="flex space-x-2 outline-none font-semibold bg-transparent  " contentEditable={true}>
            <input
              name = "name"
              value={name}
              onChange = {(e) => handleSave(e.target.value)}
              className="bg-transparent outline-none font-semibold"
              
              >
            </input>
          </div>
        );
      },
    },
    {
      accessorKey: "stock",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Stock" />
      ),
      cell: ({ row }) => {
        const [stock, setStock] = useState<number>(row.getValue("stock"));
        function handleSave(value: number){
          updateProduct({stock: value}, (row.original as any));
          setStock(value);
        }
        
        return (
          <div className="flex space-x-2 outline-none font-semibold bg-transparent  " contentEditable={true}>
          <input
            name = "stock"
            value={stock}
            onChange = {(e) => handleSave(Number(e.target.value))}
            className="bg-transparent outline-none font-semibold"
            
            >
          </input>
        </div>
        );
      },
    },
    {
      accessorKey: "price",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Precio" />
      ),
      cell: ({ row }) => {
        const [price, setPrice] = useState<number>(row.getValue("price"));
        function handleSave(value: number){
          updateProduct({price: value}, (row.original as any));
          setPrice(value);
        }
        return (
          <div className="flex space-x-2 outline-none font-semibold bg-transparent  " contentEditable={true}>
          <input
            name = "price"
            value={price}
            onChange = {(e) => handleSave(Number(e.target.value))}
            className="bg-transparent outline-none font-semibold"
            
            >
          </input>
        </div>
        );
      },
    },
    {
      accessorKey: "model",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Model" />
      ),
      cell: ({ row }) => {
        const [model, setModel] = useState<string>(row.getValue("model"));
        function handleSave(value: string){
          updateProduct({model: value}, (row.original as any));
          setModel(value);
        }
        return (
          <div className="flex space-x-2 outline-none font-semibold bg-transparent  " contentEditable={true}>
          <input
            name = "model"
            value={model}
            onChange = {(e) => handleSave(e.target.value)}
            className="bg-transparent outline-none font-semibold"
            
            >
          </input>
        </div>
        );
      },
    },
    {
      accessorKey: "size",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Tamaño" />
      ),
      cell: ({ row }) => {
        const [size, setSize] = useState<string>(row.getValue("size"));
        function handleSave(value: string){
          updateProduct({size: value}, (row.original as any));
          setSize(value);
        }
        return (
          <div className="flex space-x-2 outline-none font-semibold bg-transparent  " contentEditable={true}>
          <input
            name = "size"
            value={size}
            onChange = {(e) => handleSave(e.target.value)}
            className="bg-transparent outline-none font-semibold"
            
            >
          </input>
        </div>
        );
      },
    },
    {
      accessorKey: "material",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Material" />
      ),
      cell: ({ row }) => {
        const [material, setMaterial] = useState<string>(row.getValue("material"));
        function handleSave(value: string){
          updateProduct({material: value}, (row.original as any));
          setMaterial(value);
        }
        return (
          <div className="flex space-x-2 outline-none font-semibold bg-transparent  " contentEditable={true}>
          <input
            name = "material"
            value={material}
            onChange = {(e) => handleSave(e.target.value)}
            className="bg-transparent outline-none font-semibold"
            
            >
          </input>
        </div>
        );
      },
    },
    {
      accessorKey: "absorbency",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Absorbencia" />
      ),
      cell: ({ row }) => {
        const [absorbency, setAbsorbency] = useState<string>(row.getValue("absorbency"));
        function handleSave(value: string){
          updateProduct({absorbency: value}, (row.original as any));
          setAbsorbency(value);
        }
        return (
          <div className="flex space-x-2 outline-none font-semibold bg-transparent  " contentEditable={true}>
          <input
            name = "absorbency"
            value={absorbency}
            onChange = {(e) => handleSave(e.target.value)}
            className="bg-transparent outline-none font-semibold"
            
            >
          </input>
          </div>
        );
      },
    },
    {
      accessorKey: "material_feature",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Cuidado de la Piel" />
      ),
      cell: ({ row }) => {
        const [material_feature, setMaterial_feature] = useState<string>(row.getValue("material_feature"));
        function handleSave(value: string){
          updateProduct({material_feature: value}, (row.original as any));
          setMaterial_feature(value);
        }
        return (
          <div className="flex space-x-2 outline-none font-semibold bg-transparent  " contentEditable={true}>
          <input
            name = "material_feature"
            value={material_feature}
            onChange = {(e) => handleSave(e.target.value)}
            className="bg-transparent outline-none font-semibold"
            
            >
          </input>
          </div>
        );
      },
    },
    {
      accessorKey: "color",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Color" />
      ),
      cell: ({ row }) => {
        const [color, setColor] = useState<string>(row.getValue("color"));
        function handleSave(value: string){
          updateProduct({color: value}, (row.original as any));
          setColor(value);
        }
        return (
          <div className="flex space-x-2 outline-none font-semibold bg-transparent  " contentEditable={true}>
          <input
            name = "color"
            value={color}
            onChange = {(e) => handleSave(e.target.value)}
            className="bg-transparent outline-none font-semibold"
            
            >
          </input>
          </div>
        );
      },
    },
    {
      accessorKey: "description",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Descripción" />
      ),
      cell: ({ row }) => {
        const [description, setDescription] = useState<string>(row.getValue("description"));
        function handleSave(value: string){
          updateProduct({description: value}, (row.original as any));
          setDescription(value);
        }
        return (
          <div className="flex space-x-2 outline-none font-semibold bg-transparent  " contentEditable={true}>
          <input
            name = "description"
            value={description}
            onChange = {(e) => handleSave(e.target.value)}
            className="bg-transparent outline-none font-semibold"
            
            >
          </input>
          </div>
        );
      },
    },
    {
      accessorKey: "imagen",
      header: ({ column }) => (
        <DataTableColumnHeader column={column} title="Imagen" />
      ),
      cell: ({ row }) => {
        return (
          <AlertDialog>
            <AlertDialogTrigger asChild>
              <Button variant="outline"
              >Ver Imagen</Button>
            </AlertDialogTrigger>
            <AlertDialogContent>
              <AlertDialogHeader>
                <AlertDialogTitle>Imagen</AlertDialogTitle>
                <AlertDialogDescription>
                  <img src={(row.original as any).url} alt="imagen" />
                </AlertDialogDescription>
              </AlertDialogHeader>
              <AlertDialogFooter>
                <AlertDialogCancel>Cerrar</AlertDialogCancel>
              </AlertDialogFooter>
            </AlertDialogContent>
          </AlertDialog>
        );
      },
    },
    {
      id: "actions",
      header: "Acciones",
      cell: ({ row }) => {
        return (
          <div className="flex justify-end">
            <Dialog>
              <DialogTrigger asChild>
                <Button variant="destructive">Eliminar</Button>
              </DialogTrigger>
              <DialogContent className="sm:max-w-[425px]">
                  <DialogHeader>
                    <DialogTitle>Eliminar Producto</DialogTitle>
                    <DialogDescription>
                      Esta acción no se puede deshacer.
                    </DialogDescription>
                  </DialogHeader>
                  <DialogFooter>
                  <Button 
                    variant="destructive" 
                    onClick={() => handleDelete((row.original as any).id)}
                  >
                    Eliminar
                  </Button>
                  </DialogFooter>
              </DialogContent>
            </Dialog>
          </div>
        );
      },
    },
  ];
  