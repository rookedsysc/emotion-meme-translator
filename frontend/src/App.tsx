import "./App.css";
import MemeGeneratorMain from "./components/MemeGeneratorMainPage";
import { useState } from "react";

const App = () => {
  const [selectedTemplate, setSelectedTemplate] = useState<number | null>(null);

  const handleTemplateSelect = (id: number) => {
    setSelectedTemplate(id);
  };

  return (
    <div>
      <MemeGeneratorMain onTemplateSelect={handleTemplateSelect} />
    </div>
  );
};

export default App;
