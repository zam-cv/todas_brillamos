import React, {useState, useEffect} from 'react';   
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import  { Plus, Minus } from 'lucide-react';


const DynamicInputFields = ({ description, setDescription } : any) => {
    const [inputFields, setInputFields] = useState(
      description ? description.split(' | ') : ['']
    );

    const handleAddField = () => {
        setInputFields([...inputFields, '']);
    };

    const handleRemoveField = (index: any) => {
        const newInputFields = inputFields.filter((_: any, i: any) => i !== index);
        setInputFields(newInputFields);
    };

    const handleInputChange = (index: any, value: any) => {
        const newInputFields = [...inputFields];
        newInputFields[index] = value;
        setInputFields(newInputFields);
    };

    useEffect(() => {
        const concatenatedDescription = inputFields.join(' | ');
        setDescription(concatenatedDescription);
    }, [inputFields, setDescription]);

    return (
        <div className='flex flex-col w-full max-w-sm space-y-2'>
           {inputFields.map((field: any, index: any) => (
            <div key={index} className='flex w-full max-w-sm items-center space-x-1'>
                {index === inputFields.length - 1  ? (
                    <Button 
                        className="bg-transparent hover:bg-transparent w-1/10 opacity-0 hover:opacity-100"
                        onClick = {handleAddField}
                    >
                        <Plus color="#949494" size="12"/>
                    </Button>
                ): (
                    <Button
                        className='bg-transparent hover:bg-transparent invisible hover:visible w-1/10 '
                        onClick={() => handleRemoveField(index)}
                        >
                        <Minus color="#949494" size="12"/>
                        </Button>
                )}
                <Input  
                    value={field}
                    onChange={(e) => handleInputChange(index, e.target.value)}
                    placeholder={index === 0 ? "Descripción del producto" : `Descripción del producto`}
                    />
                </div>
           ))}
        </div>
    );
}

export default DynamicInputFields;