import './App.css'
import { Route, Routes } from 'react-router-dom';
import MemeGeneratorMain from './components/MemeGeneratorMainPage';

function App() {
  return (
    <Routes>
      <Route path="/" element={<MemeGeneratorMain onTemplateSelect={() => {}} />} />
      <Route path="/generate/:templateId" element={<MemeGeneratorMain onTemplateSelect={() => {}} />} />
    </Routes>
  )
}

export default App
