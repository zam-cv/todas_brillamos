import { Button } from "@/components/ui/button"
import { IoIosAddCircleOutline } from "react-icons/io";


export default function Specialists() {
  return (
    <div>
       <h2 className="scroll-m-20 border-b pb-2 text-3xl font-semibold tracking-tight first:mt-0">
          Especialistas
      </h2>   
      <br></br>
      <div className="absolute overflow-auto flex flex-col gap-5 ">
          <div>
            <Button>
              <IoIosAddCircleOutline className="mr-2 h-4 w-4"/>Añadir Especialista
            </Button>
          </div>
          <div>
            <Button>
              <IoIosAddCircleOutline className="mr-2 h-4 w-4"/>Añadir Producto
            </Button>
          </div>
          <div>
            <Button>
              <IoIosAddCircleOutline className="mr-2 h-4 w-4"/>Añadir Etiqueta
            </Button>
          </div>
          <div>
            <Button>
              <IoIosAddCircleOutline className="mr-2 h-4 w-4"/>Añadir Artículo
            </Button>
          </div>
       
      </div>
      
      
    </div>
  )
}