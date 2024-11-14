const TEMPLATES = [
  {
    id: 0,
    title: '저쪽 집이 무너졌다고 해서 구경하러 갔죠.',
    image: '/images/sample1.jpeg',
    description: '저쪽 집이 무너졌다고 해서 구경하러 갔죠.\n그런데 보고오니 우리 집이 무너진 거예요. 보자마자 눈물이 났어요.',
  },
  {
    id: 1,
    title: '안녕히 계세요 여러분 ~',
    image: '/images/sample2.jpeg',
    description: '가영이 안녕히 계세요 여러분~ 짤',
  },
  {
    id: 2,
    title: '난 가끔 눈물을 흘린다.',
    image: '/images/sample3.png',
    description: '난… ㄱㅏ끔… 눈물을 흘린ㄷㅏ…',
  },
];

interface Props {
  onTemplateSelect: (id: number) => void;
}

const MemeGeneratorMain = ({ onTemplateSelect }: Props) => {
  return (
    <div className="w-full min-h-screen bg-white">
      {/* 헤더 */}
      <div className="w-full p-4 flex justify-center">
        <img 
          src="/images/logo.webp" 
          alt="Meme Generator Logo" 
          className="w-4/5 h-auto max-h-80 object-fill rounded-3xl"
        />
      </div>

      {/* 메인 콘텐츠 */}
      <div className="max-w-7xl mx-auto px-4 py-8">
        <div className="flex flex-wrap -mx-4">
          {TEMPLATES.map((template) => (
            <div 
              key={template.id}
              className="w-full sm:w-1/2 lg:w-1/3 p-4"
              onClick={() => onTemplateSelect(template.id)}
            >
              <div className="bg-white rounded-xl shadow-lg overflow-hidden cursor-pointer hover:shadow-xl transition-shadow h-full">
                <div className="relative pt-[75%]">
                  <img
                    src={template.image}
                    alt={template.title}
                    className="absolute inset-0 w-full h-full object-contain bg-white"
                  />
                </div>
                <div className="p-4">
                  <h2 className="text-lg font-semibold mb-2">{template.title}</h2>
                  <p className="text-gray-600 text-sm mb-4 line-clamp-2">{template.description}</p>
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
