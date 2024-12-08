import { HelmetProvider } from "react-helmet-async";
import "./App.css";
import MemeGeneratorMain from "./components/MemeGeneratorMainPage";

const App = () => {
  return (
    <div>
      <HelmetProvider>
        <MemeGeneratorMain />
      </HelmetProvider>
    </div>
  );
};

export default App;
