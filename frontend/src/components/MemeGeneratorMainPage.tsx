import { useEffect, useState } from "react";
import { Emotion } from "../types/emotion";
import { fetchEmotions, getImageUrl } from "../api/emotions";

interface Props {
  onTemplateSelect: (id: number) => void;
}

const MemeGeneratorMain = ({ onTemplateSelect }: Props) => {
  const [templates, setTemplates] = useState<Emotion[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const loadEmotions = async () => {
      try {
        const data = await fetchEmotions();
        setTemplates(data);
        setLoading(false);
      } catch (err) {
        setError("템플릿을 불러오는데 실패했습니다.");
        setLoading(false);
      }
    };

    loadEmotions();
  }, []);

  if (loading) {
    return (
      <div className="w-full min-h-screen bg-white flex items-center justify-center">
        <div className="text-xl">로딩중...</div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="w-full min-h-screen bg-white flex items-center justify-center">
        <div className="text-xl text-red-500">{error}</div>
      </div>
    );
  }

  return (
    <div className="w-full min-h-screen bg-white">
      <div className="w-full p-4 flex justify-center">
        <img
          src="/images/logo.webp"
          alt="Meme Generator Logo"
          className="w-4/5 h-auto max-h-80 object-fill rounded-3xl"
        />
      </div>
      <div className="max-w-7xl mx-auto px-4 py-8">
        <div className="flex flex-wrap -mx-4">
          {templates.map((template) => (
            <div
              key={template.id}
              className="w-full sm:w-1/2 lg:w-1/3 p-4"
              onClick={() => onTemplateSelect(template.id)}
            >
              <div className="bg-white rounded-xl shadow-lg overflow-hidden cursor-pointer hover:shadow-xl transition-shadow h-full">
                <div className="relative pt-[75%]">
                  <img
                    src={getImageUrl(template.image)}
                    alt={template.title}
                    className="absolute inset-0 w-full h-full object-contain bg-white"
                  />
                </div>
                <div className="p-4">
                  <h2 className="text-lg font-semibold mb-2">
                    {template.title}
                  </h2>
                  <p className="text-gray-600 text-sm mb-4 line-clamp-2">
                    {template.description}
                  </p>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default MemeGeneratorMain;
