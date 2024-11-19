import { useEffect, useRef, useState } from "react";
import { Emotion } from "../types/emotion";
import { fetchEmotions, getImageUrl } from "../api/emotions";
import API_IP from "../common/ApiIp";

interface GeneratedMeme {
  templateId: number;
  originalText: string;
  transformedText: string;
  templateImage: string;
}

const MemeGeneratorMain = () => {
  const [templates, setTemplates] = useState<Emotion[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [activeTemplate, setActiveTemplate] = useState<number | null>(null);
  const [userInput, setUserInput] = useState("");
  const [generatedMeme, setGeneratedMeme] = useState<GeneratedMeme | null>(
    null
  );
  const [apiLoading, setApiLoading] = useState(false);
  const selectedTemplateRef = useRef<HTMLDivElement>(null);

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

  useEffect(() => {
    if (activeTemplate !== null && selectedTemplateRef.current) {
      const modalHeight = 280;
      const windowHeight = window.innerHeight;
      const templateBottom =
        selectedTemplateRef.current.getBoundingClientRect().bottom;

      if (templateBottom + modalHeight > windowHeight) {
        window.scrollTo({
          top: window.scrollY + (templateBottom + modalHeight - windowHeight),
          behavior: "smooth",
        });
      }
    }
  }, [activeTemplate]);

  const handleTemplateClick = (id: number) => {
    if (activeTemplate === id) {
      setActiveTemplate(null);
    } else {
      setActiveTemplate(id);
    }
    setGeneratedMeme(null);
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (userInput.trim() && activeTemplate !== null) {
      setApiLoading(true);
      try {
        const response = await fetch(
          `http://${API_IP}:8080/translator/translate`,
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              accept: "*/*",
            },
            body: JSON.stringify({
              id: activeTemplate,
              prompt: userInput.trim(),
            }),
          }
        );

        const data = await response.json();

        const selectedTemplate = templates.find((t) => t.id === activeTemplate);
        if (selectedTemplate) {
          setGeneratedMeme({
            templateId: activeTemplate,
            originalText: userInput,
            transformedText: data.response,
            templateImage: getImageUrl(selectedTemplate.image),
          });
        }

        setUserInput("");
        setActiveTemplate(null);
      } catch (err) {
        setError("밈 생성에 실패했습니다.");
      } finally {
        setApiLoading(false);
      }
    }
  };

  const handleReset = () => {
    setGeneratedMeme(null);
    setActiveTemplate(null);
    setUserInput("");
    setError(null);
  };

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

  if (generatedMeme) {
    return (
      <div className="w-full min-h-screen bg-white p-4">
        <div className="max-w-3xl mx-auto bg-white rounded-xl shadow-lg overflow-hidden">
          <div className="relative pt-[75%]">
            <img
              src={generatedMeme.templateImage}
              alt="Selected Template"
              className="absolute inset-0 w-full h-full object-contain bg-white"
            />
          </div>
          <div className="p-6 space-y-4">
            <div className="text-gray-500">원본 텍스트:</div>
            <div className="text-lg font-medium">
              {generatedMeme.originalText}
            </div>
            <div className="text-gray-500">변환된 텍스트:</div>
            <div className="text-xl font-semibold">
              {generatedMeme.transformedText}
            </div>
            <div className="flex justify-center pt-4">
              <button
                onClick={handleReset}
                className="px-6 py-3 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-colors"
              >
                다시하기
              </button>
            </div>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="w-full min-h-screen bg-white relative pb-[280px]">
      <div className="w-full p-4 flex justify-center">
        <img
          src="/images/logo.webp"
          alt="Meme Generator Logo"
          className="w-4/5 h-auto max-h-80 object-fill rounded-3xl"
        />
      </div>
      <div className="max-w-7xl mx-auto px-4 py-8">
        <div className="flex flex-wrap -mx-4">
          {templates.map((template) => {
            const isSelected = activeTemplate === template.id;
            const shouldBlur = activeTemplate !== null && !isSelected;

            return (
              <div
                key={template.id}
                className="w-full sm:w-1/2 lg:w-1/3 p-4"
                onClick={() => handleTemplateClick(template.id)}
                ref={isSelected ? selectedTemplateRef : null}
              >
                <div
                  className={`bg-white rounded-xl shadow-lg overflow-hidden cursor-pointer hover:shadow-xl transition-all duration-300 h-full
                    ${shouldBlur ? "blur-sm" : ""}
                    ${isSelected ? "ring-4 ring-blue-500 scale-105" : ""}`}
                >
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
            );
          })}
        </div>
      </div>

      {activeTemplate !== null && (
        <div className="fixed bottom-0 left-0 right-0 bg-white shadow-lg rounded-t-3xl p-6 transform transition-transform duration-300 ease-in-out">
          <form onSubmit={handleSubmit} className="space-y-4">
            <textarea
              value={userInput}
              onChange={(e) => setUserInput(e.target.value)}
              placeholder="당신의 이야기를 들려주세요..."
              className="w-full p-4 border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-blue-500 resize-none h-32"
            />
            <div className="flex justify-end gap-4">
              <button
                type="button"
                onClick={handleReset}
                className="px-6 py-2 bg-gray-200 rounded-lg hover:bg-gray-300 transition-colors"
              >
                취소
              </button>
              <button
                type="submit"
                disabled={apiLoading}
                className={`px-6 py-2 bg-blue-500 text-white rounded-lg transition-colors
                  ${apiLoading ? "bg-blue-400 cursor-not-allowed" : "hover:bg-blue-600"}`}
              >
                {apiLoading ? "생성 중..." : "생성하기"}
              </button>
            </div>
          </form>
        </div>
      )}
    </div>
  );
};

export default MemeGeneratorMain;
