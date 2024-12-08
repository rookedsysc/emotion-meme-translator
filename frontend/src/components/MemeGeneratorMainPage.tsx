import { useEffect, useRef, useState } from "react";
import { Helmet } from "react-helmet-async";
import { Emotion } from "../types/emotion";
import { fetchEmotions, getImageUrl } from "../api/emotions";
import ShareButton from "./ShareButton";
import { GeneratedMeme } from "../types/meme";
//import API_IP from "../common/ApiIp";

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
        console.error(err);
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
        const response = await fetch("/translator/translate", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            accept: "*/*",
          },
          body: JSON.stringify({
            id: activeTemplate,
            prompt: userInput.trim(),
          }),
        });

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
        console.error(err);
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

  const getMetaTags = () => {
    if (generatedMeme) {
      return (
        <Helmet>
          <title>{generatedMeme.transformedText} | 밈 생성기</title>
          <meta
            name="description"
            content={`${generatedMeme.originalText} → ${generatedMeme.transformedText}`}
          />
          <meta
            property="og:title"
            content={`${generatedMeme.transformedText} | 밈 생성기`}
          />
          <meta
            property="og:description"
            content={generatedMeme.originalText}
          />
          <meta property="og:image" content={generatedMeme.templateImage} />
        </Helmet>
      );
    }

    return (
      <Helmet>
        <title>나만의 밈 만들기 | 밈 생성기</title>
        <meta
          name="description"
          content="AI 기반 밈 생성기로 재미있는 밈을 만들어보세요. 다양한 템플릿으로 당신만의 유머러스한 밈을 제작할 수 있습니다."
        />
        <meta property="og:title" content="나만의 밈 만들기 | 밈 생성기" />
        <meta
          property="og:description"
          content="AI 기반 밈 생성기로 재미있는 밈을 만들어보세요."
        />
        <meta property="og:image" content="/images/logo.webp" />
        <meta name="keywords" content="밈, 밈 생성기, AI 밈, 유머, 짤방" />
      </Helmet>
    );
  };

  if (loading) {
    return (
      <main className="w-full min-h-screen bg-white flex items-center justify-center">
        {getMetaTags()}
        <div className="text-xl">로딩중...</div>
      </main>
    );
  }

  if (error) {
    return (
      <main className="w-full min-h-screen bg-white flex items-center justify-center">
        {getMetaTags()}
        <div className="text-xl text-red-500">{error}</div>
      </main>
    );
  }

  if (generatedMeme) {
    return (
      <main className="w-full min-h-screen bg-white p-4">
        {getMetaTags()}
        <article className="max-w-3xl mx-auto bg-white rounded-xl shadow-lg overflow-hidden">
          <figure className="relative pt-[75%]">
            <img
              src={generatedMeme.templateImage}
              alt={generatedMeme.transformedText}
              className="absolute inset-0 w-full h-full object-contain bg-white"
            />
          </figure>
          <div className="p-6 space-y-4">
            <section>
              <h2 className="text-gray-500">원본 텍스트:</h2>
              <p className="text-lg font-medium">
                {generatedMeme.originalText}
              </p>
            </section>
            <section>
              <h2 className="text-gray-500">변환된 텍스트:</h2>
              <p className="text-xl font-semibold">
                {generatedMeme.transformedText}
              </p>
            </section>
            <div className="flex justify-center gap-4 pt-4">
              <button
                onClick={handleReset}
                className="px-6 py-3 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-colors"
              >
                다시하기
              </button>
              <ShareButton meme={generatedMeme} />
            </div>
          </div>
        </article>
      </main>
    );
  }

  return (
    <main className="w-full min-h-screen bg-white relative pb-[280px]">
      {getMetaTags()}
      <header className="w-full p-4 flex justify-center">
        <img
          src="/images/logo.webp"
          alt="밈 생성기 로고"
          className="w-4/5 h-auto max-h-80 object-fill rounded-3xl"
        />
      </header>
      <section className="max-w-7xl mx-auto px-4 py-8">
        <h1 className="sr-only">밈 템플릿 목록</h1>
        <div className="flex flex-wrap -mx-4">
          {templates.map((template) => {
            const isSelected = activeTemplate === template.id;
            const shouldBlur = activeTemplate !== null && !isSelected;

            return (
              <article
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
                  <figure className="relative pt-[75%]">
                    <img
                      src={getImageUrl(template.image)}
                      alt={template.title}
                      className="absolute inset-0 w-full h-full object-contain bg-white"
                    />
                  </figure>
                  <div className="p-4">
                    <h2 className="text-lg font-semibold mb-2">
                      {template.title}
                    </h2>
                    <p className="text-gray-600 text-sm mb-4 line-clamp-2">
                      {template.description}
                    </p>
                  </div>
                </div>
              </article>
            );
          })}
        </div>
      </section>

      {activeTemplate !== null && (
        <section className="fixed bottom-0 left-0 right-0 bg-white shadow-lg rounded-t-3xl p-6 transform transition-transform duration-300 ease-in-out">
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
        </section>
      )}
    </main>
  );
};

export default MemeGeneratorMain;
