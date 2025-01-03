import { Copy, Share } from "lucide-react";
import { GeneratedMeme } from "../types/meme";

interface ShareButtonProps {
  meme: GeneratedMeme;
}

const ShareButton = ({ meme }: ShareButtonProps) => {
  const getShareContent = () => {
    return `${meme.transformedText}\n\n밈 생성기를 활용해서 내 기분을 밈으로 표현해보세요!\n${window.location.href}`;
  };

  const handleShare = async () => {
    if (!navigator.share) {
      alert("브라우저가 공유 기능을 지원하지 않습니다.");
      return;
    }

    try {
      await navigator.share({
        text: getShareContent(),
      });
    } catch (error) {
      if (error instanceof Error) {
        if (error.name === "NotAllowedError") {
          alert("공유 권한이 거부되었습니다.");
        } else if (error.name !== "AbortError") {
          alert("공유하는 도중 오류가 발생했습니다.");
        }
        console.log(error);
      }
    }
  };

  const handleCopy = async () => {
    try {
      await navigator.clipboard.writeText(getShareContent());
      alert("텍스트가 클립보드에 복사되었습니다!");
    } catch (error) {
      alert("복사하는 도중 오류가 발생했습니다.");
      console.error("복사 오류:", error);
    }
  };

  return (
    <div className="flex gap-2">
      <button
        onClick={handleShare}
        className="flex items-center gap-2 px-6 py-3 bg-emerald-500 text-white rounded-lg hover:bg-emerald-600 transition-colors"
      >
        <Share size={20} />
        공유하기
      </button>
      <button
        onClick={handleCopy}
        className="flex items-center gap-2 px-6 py-3 bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition-colors"
      >
        <Copy size={20} />
        복사하기
      </button>
    </div>
  );
};

export default ShareButton;
