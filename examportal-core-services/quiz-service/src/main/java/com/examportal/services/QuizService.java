package com.examportal.services;

import com.examportal.constant.MessageConstant;
import com.examportal.dtos.QuizDTO;
import com.examportal.dtos.UpdateQuizDTO;
import com.examportal.entities.Category;
import com.examportal.entities.Quiz;
import com.examportal.repositories.CategoryRepository;
import com.examportal.repositories.QuizRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class QuizService {

    private final QuizRepository quizRepository;

    private final ModelMapper modelMapper;

    private final CategoryRepository categoryRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository,
                       ModelMapper modelMapper,
                       CategoryRepository categoryRepository) {
        this.quizRepository = quizRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    public Object addQuiz(QuizDTO quizDTO) {
        Quiz quiz = new Quiz();
        quiz.setTitle(quizDTO.getTitle());
        quiz.setDescription(quizDTO.getDescription());
        quiz.setMaxMarks(quizDTO.getMaxMarks());
        quiz.setNumberOfQuestions(quizDTO.getNumberOfQuestions());
        Optional<Category> categoryOptional = categoryRepository.findById(quizDTO.getCat_id());
        if (!categoryOptional.isPresent()) {
            return MessageConstant.CATEGORY_NOT_FOUND;
        }
        quiz.setCategory(categoryOptional.get());
        return quizRepository.save(quiz);
    }

    public String deleteQuiz(long id) {
        Optional<Quiz> optionalQuiz = quizRepository.findById(id);
        if (optionalQuiz.isPresent()) {
            quizRepository.delete(optionalQuiz.get());
            return MessageConstant.DELETE_QUIZ_SUCCESS;
        }
        return MessageConstant.QUIZ_NOT_FOUND;
    }

    public List<Quiz> getAllQuiz() {
        return quizRepository.findAll();
    }

    public Quiz getQuiz(long id) {
        return quizRepository.findById(id).orElse(null);
    }

    public Quiz updateQuiz(UpdateQuizDTO updateQuizDTO) {
        Optional<Quiz> optionalQuiz = quizRepository.findById(updateQuizDTO.getId());
        if (optionalQuiz.isPresent()) {
            Quiz quiz = optionalQuiz.get();
            quiz.setTitle(updateQuizDTO.getTitle() != null ? updateQuizDTO.getTitle() : quiz.getTitle());
            quiz.setDescription(updateQuizDTO.getDescription() != null ? updateQuizDTO.getDescription() : quiz.getDescription());
            quiz.setMaxMarks(updateQuizDTO.getMaxMarks() != null ? updateQuizDTO.getMaxMarks() : quiz.getMaxMarks());
            quiz.setNumberOfQuestions(updateQuizDTO.getNumberOfQuestions() != null ? updateQuizDTO.getNumberOfQuestions() : quiz.getNumberOfQuestions());
            return quizRepository.save(quiz);
        }
        return null;
    }

    public boolean quizExists(long id){
       return quizRepository.existsById(id);
    }
}
