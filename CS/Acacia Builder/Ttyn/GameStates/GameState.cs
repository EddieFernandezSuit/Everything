
using Audrey;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.Graphics;
using System;
using Tyn;
using Tyn.objects;
using Tyn.objects.Component;
using Tyn.objects.System;
using Tyn.Objects.Systems;


public abstract class GameState {
    protected GameStateManager gsm;
    protected SpriteBatch spriteBatch;

    protected Texture2D cursorSpr;

    protected RenderSystem rs;
    protected TextSystem ts;
    protected CursorSystem cs;
    protected ClickableSystem cls;
    protected RectangleSystem res;
    protected TransformSystem tras;
    protected CampSystem cas;
    protected PlayerSystem ps;

    protected Engine e1 = new Engine();
    protected Game1 game;

    public Entity creature1;

    public ContentManager Content
{
    get;
    private set;
}
    protected GameState(Game1 game, GameStateManager gsm){
        this.gsm = gsm;
        this.game = game;
        Content = new ContentManager(game.Services);
        Content.RootDirectory = "Content";
        init();
    }
    public Texture2D loadTexture(string str)
    {
        return Content.Load<Texture2D>(str);
    }
    public Entity MakeText(Vector2 pos, string str)
    {
        SpriteFont font = Content.Load<SpriteFont>("font");
        Entity healthNum = e1.CreateEntity();
        healthNum.AddComponent(new TransformComponent());
        healthNum.AddComponent(new TextComponent());
        healthNum.GetComponent<TransformComponent>().position = pos;
        healthNum.GetComponent<TextComponent>().font = font;
        healthNum.GetComponent<TextComponent>().str = str;
        healthNum.GetComponent<TextComponent>().color = Color.Black;
        healthNum.GetComponent<TextComponent>().normColor = Color.Black;
        return healthNum;
    }
    public void MakeCursor()
    {
        Entity cursorEnt = e1.CreateEntity();
        cursorEnt.AddComponent(new TransformComponent());
        cursorEnt.AddComponent(new CursorComponent());
        cursorEnt.AddComponent(new TextureComponent());
        cursorEnt.AddComponent(new RectangleComponent());
        cursorEnt.GetComponent<TextureComponent>().texture = cursorSpr;
    }
    public Entity MakeClickable(Vector2 pos, string strin, Action action)
    {
        Entity fontEnt = MakeText(pos, strin);
        fontEnt.AddComponent(new ClickableComponent());
        fontEnt.GetComponent<ClickableComponent>().action = action;
        return fontEnt;
    }
    public void clickEmpty()
    {

    }
    public virtual void init()
    {
        // Create a new SpriteBatch, which can be used to draw textures.
        
        spriteBatch = new SpriteBatch(game.GraphicsDevice);
        rs = new RenderSystem(e1, spriteBatch);
        ts = new TextSystem(e1, spriteBatch);
        cs = new CursorSystem(e1, spriteBatch);
        cls = new ClickableSystem(e1);
        res = new RectangleSystem(e1);
        tras = new TransformSystem(e1);
        cas = new CampSystem(e1, Content);
        ps = new PlayerSystem(e1);

        cursorSpr = Content.Load<Texture2D>("cursor");

        MakeCursor();

    }
    
    public virtual void update(GameTime gameTime)
    {
        handleInput();
        cs.update(gameTime);
        cls.update(gameTime);
        res.update(gameTime);
        tras.update(gameTime);
        cas.update(gameTime);
        ps.update(gameTime);
    }
    public virtual void draw()
    {
        rs.draw();
        ts.draw();
        cs.draw();
    }
    public abstract void handleInput();
}